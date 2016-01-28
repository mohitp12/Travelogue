// Gets the public transportion details from the database using source and destination places
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace travelWcf
{
    public class getBus
    {
        public string source { get; set; }
        public string via { get; set; }
        public string destination { get; set; }
        public string time { get; set; }
    }
}
