using Bookish.Models;
using Microsoft.Practices.Prism.ViewModel;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Bookish.ViewModels
{
    public class AuthorViewModel : ViewModelBase
    {
        private BooksViewModel _bookViewModel;

        public AuthorViewModel()
        {
            _bookViewModel = new BooksViewModel();
            Authors = new ObservableCollection<Author>(_bookViewModel.Books.Select(b => b.Author).Distinct());
        }

        public ObservableCollection<Author> Authors { get; private set; }
    }
}
