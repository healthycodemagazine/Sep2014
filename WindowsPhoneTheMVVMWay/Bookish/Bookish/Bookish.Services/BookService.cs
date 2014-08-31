using Bookish.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Bookish.Services
{
    public class BookService
    {
        public async Task<List<Book>> GetBooks()
        {
            return new List<Book> 
            {
                new Book
                {
                    Name = "The Adventures of Sherlock Holmes",
                    Genre = "Mystery",
                    Price = 100,
                    Author = new Author
                    {
                        FirstName = "AC",
                        LastName = "Doyle"
                    },
                    ImageUrl = @"http://inpossibility.files.wordpress.com/2014/05/holmes_adv.jpg"
                },
                new Book
                {
                    Name = "Game of Thrones",
                    Genre = "Adventure",
                    Price = 200,
                    Author = new Author
                    {
                        FirstName = "George",
                        LastName = "Martin"
                    },
                    ImageUrl = @"http://fantasy-faction.com/wp-content/uploads/2014/02/GOT-TV.jpg"
                },
                new Book
                {
                    Name = "Hound of Baskervilles",
                    Genre = "Mystery",
                    Price = 150,
                    Author = new Author
                    {
                        FirstName = "AC",
                        LastName = "Doyle"
                    },
                    ImageUrl = @"http://1.bp.blogspot.com/_GaNPg9M10nU/TBmNDhEL9iI/AAAAAAAAA5Y/RQpLpBDHOXU/s1600/hound-basker.jpg"
                }
            };
        }
    }
}
