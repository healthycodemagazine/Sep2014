/*
* A sample exercise for working with Akka and Scala. Feel free to make changes.
* http://healthycodemagazine.com
*/
import java.io._
import scala.io.Source
import java.sql.DriverManager
import java.sql.Statement
import akka.actor._
import akka.routing._

trait CommonTrait {def fileName:String}
case class DBCredentials(fileName:String,endpoint:String,username:String,password:String) extends CommonTrait
case class URLS(fileName:String,ebs:String,gae:String,ec2iis:String)extends CommonTrait
case class Iaas(fileName:String,ip:String,username:String,password:String,accessKey:String,secretId:String) extends CommonTrait
case class DBErrorLevel1(fileName:String,errorMessage:String)extends CommonTrait

class OutputFolderLevel2Actor extends Actor{
	private val testOutputFilesFolder = "./src/main/resources/OutputFiles2"

	def receive = {
		case iaas:Iaas => {
			val writer = new PrintWriter(new File(testOutputFilesFolder + "/" + iaas.fileName ))
		    writer.write(iaas.ip)
		    writer.write("\n")
		    writer.write(iaas.username)
		    writer.write("\n")
			writer.write(iaas.password)
		    writer.write("\n")
			writer.write(iaas.accessKey)
		    writer.write("\n")
			writer.write(iaas.secretId)
		    writer.close()			
		    println(s"${iaas.fileName} success")
		}
	}
}
class OutputFolderLevel1Actor extends Actor{
	private val testOutputFilesFolder = "./src/main/resources/OutputFiles1"
	private val testErrorFilesFolder = "./src/main/resources/ErrorFiles"

	def receive = {
		case urls:URLS => {
			val writer = new PrintWriter(new File(testOutputFilesFolder + "/" + urls.fileName ))
		    writer.write(urls.ebs)
		    writer.write("\n")
		    writer.write(urls.gae)
		    writer.write("\n")
		    writer.write(urls.ec2iis)
		    writer.close()			
		    println(s"${urls.fileName} success")
		}
		case dbErrorLevel1:DBErrorLevel1 => {
			val writer = new PrintWriter(new File(testErrorFilesFolder + "/" + dbErrorLevel1.fileName ))
			writer.write(dbErrorLevel1.errorMessage)
		    writer.close()
		    println(s"${dbErrorLevel1.fileName} ERROR")				
		}
	}
}
class MySQLProcessingActor(outputFolderLevel1Actor:ActorRef,outputFolderLevel2Actor:ActorRef) extends Actor{
	def accessTable1(stmt:Statement):(String,String) = {
		val rs1 = stmt.executeQuery("select * from table1")
		rs1.next()
		val url1 = rs1.getString("url")
		rs1.next()
		val url2 = rs1.getString("url")
		(url1,url2)
	}
	def accessTable2(stmt:Statement):(String,String,String,String,String) = {
		val rs = stmt.executeQuery("select * from table2")
		rs.next()
		(rs.getString("ip"),rs.getString("username"),rs.getString("password"),rs.getString("accesskey"),rs.getString("secretkey"))
	}
	def receive = {
		case credentials:DBCredentials => {
			Class.forName("com.mysql.jdbc.Driver")
			try{
					val url = "jdbc:mysql://" + credentials.endpoint + "/assignment"
					val con = DriverManager.getConnection(url,credentials.username,credentials.password)
					val stmt = con.createStatement()
					val paasUrls = accessTable1(stmt)
					val iaasData = accessTable2(stmt)
					val url1 = paasUrls._1
					val url2 = paasUrls._2
					val url3 = "http://" + iaasData._1
					val urls:URLS = new URLS(credentials.fileName,url1,url2,url3)
					val iaas:Iaas = new Iaas(credentials.fileName,iaasData._1,iaasData._2,iaasData._3,iaasData._4,iaasData._5)
					outputFolderLevel1Actor ! urls
					outputFolderLevel2Actor ! iaas
					stmt.close()
					con.close()				
			}
			catch{
				case ex:Exception => {
					val dbErrorLevel1:DBErrorLevel1 = new DBErrorLevel1(credentials.fileName,ex.getMessage)
					outputFolderLevel1Actor ! dbErrorLevel1
				}
			}

		}
	}
}
class InputFileProcessingActor(db:ActorRef) extends Actor{
	def receive = {
		case file:File => {
			val lines = Source.fromFile(file).getLines.toList
			val credentials = new DBCredentials(file.getName,lines(0),lines(1),lines(2))
			println("*** " + self.path.name + ": " + credentials.fileName)
			db ! credentials
		}
		case _ => println("Cool") 
	}
}

object ScalaEx extends App{
	def getInfo(file:File):DBCredentials= {
		val lines = Source.fromFile(file).getLines.toList
		val credentials = new DBCredentials(file.getName,lines(0),lines(1),lines(2))
		credentials
	}
	val inputFolder = "./src/main/resources/Files"
	val system = ActorSystem("ScalaEx")

	val outputFolderLevel1Actors = system.actorOf(Props[OutputFolderLevel1Actor].withRouter(RoundRobinRouter(60)),name="OutputActors1")
	val outputFolderLevel2Actors = system.actorOf(Props[OutputFolderLevel2Actor].withRouter(RoundRobinRouter(60)),name="OutputActors2")
	val mySQLProcessingActors = system.actorOf(Props(new MySQLProcessingActor(outputFolderLevel1Actors,outputFolderLevel2Actors)).withRouter(RoundRobinRouter(60)),name="MySQLActors")
	val simpleRouted = system.actorOf(Props(new InputFileProcessingActor(mySQLProcessingActors)).withRouter(
                        RoundRobinRouter(60)
                     ), name = "ScalaExercise")
	for(file <- new File(inputFolder).listFiles) {
			simpleRouted ! file
	}
}
