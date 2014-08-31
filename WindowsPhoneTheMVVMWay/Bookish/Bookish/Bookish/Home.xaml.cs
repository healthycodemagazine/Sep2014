using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Navigation;
using Microsoft.Phone.Controls;
using Microsoft.Phone.Shell;
using Bookish.ViewModels;

namespace Bookish
{
    public partial class Home : PhoneApplicationPage
    {
        public Home()
        {
            InitializeComponent();
            var booksViewModel = new BooksViewModel();
            booksViewModel.LoadCommand.Execute(null);
            BooksPivot.DataContext = booksViewModel;
        }
    }
}