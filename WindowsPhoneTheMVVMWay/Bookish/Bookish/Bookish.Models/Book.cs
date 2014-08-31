using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Linq.Expressions;
using System.Text;

namespace Bookish.Models
{
    public class Book : NotifyingObject
    {
        private double _price;
        private string _name;
        private Author _author;
        private string _genre;
        private string _imageUrl;

        public string Name
        {
            get
            {
                return _name;
            }
            set
            {
                if (_name != value)
                {
                    _name = value;
                    RaisePropertyChanged(() => Name);
                }
            }
        }

        public Author Author
        {
            get
            {
                return _author;
            }
            set
            {
                if (_author != value)
                {
                    _author = value;
                    RaisePropertyChanged(() => Author);
                }
            }
        }

        public double Price
        {
            get
            {
                return _price;
            }
            set
            {
                if (_price != value)
                {
                    _price = value;
                    RaisePropertyChanged(() => Price);
                }
            }
        }

        public string Genre
        {
            get
            {
                return _genre;
            }
            set
            {
                if (_genre != value)
                {
                    _genre = value;
                    RaisePropertyChanged(() => Genre);
                }
            }
        }

        public string ImageUrl
        {
            get { return _imageUrl; }
            set
            {
                if (_imageUrl != value)
                {
                    _imageUrl = value;
                    RaisePropertyChanged(() => ImageUrl);
                }
            }
        }
    }
}
