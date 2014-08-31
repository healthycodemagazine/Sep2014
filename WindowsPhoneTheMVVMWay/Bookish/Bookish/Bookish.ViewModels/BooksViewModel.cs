using Bookish.Models;
using Bookish.Services;
using Microsoft.Practices.Prism.Commands;
using Microsoft.Practices.Prism.ViewModel;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Windows.Input;

namespace Bookish.ViewModels
{
    public class BooksViewModel : DataLoaderViewModel
    {
        private readonly BookService _bookService;
        private ObservableCollection<Book> _books;
        private Book _selectedBook;

        public BooksViewModel()
        {
            _bookService = new BookService();
            RefreshBooksCommand = new DelegateCommand(LoadData);
            NavigateToAmazonCommand =
                new DelegateCommand<string>(NavigateToAmazon);
        }

        private void NavigateToAmazon(string url)
        {
            //Your navigation logic goes here
        }

        public ICommand NavigateToAmazonCommand { get; set; }

        public ICommand RefreshBooksCommand { get; private set; }

        public ObservableCollection<Book> Books
        {
            get { return _books; }
            private set
            {
                if (_books != value)
                {
                    _books = value;
                    RaisePropertyChanged(() => Books);
                }
            }
        }

        public Book SelectedBook
        {
            get
            {
                return _selectedBook;
            }
            set
            {
                if (_selectedBook != value)
                {
                    _selectedBook = value; 
                    RaisePropertyChanged(() => SelectedBook);
                }
            }
        }

        private async void LoadData()
        {
            var booksResponse = await _bookService.GetBooks();
            Books = new ObservableCollection<Book>(booksResponse);
        }
    }

    public abstract class DataLoaderViewModel : ViewModelBase
    {
        protected DataLoaderViewModel()
        {
            LoadCommand = new DelegateCommand(LoadData);
        }

        public ICommand LoadCommand { get; private set; }

        protected abstract void LoadData();
    }
}
