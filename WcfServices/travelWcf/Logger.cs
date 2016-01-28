//This is just the logger file which can help in testing. 

using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.IO;
namespace travelWcf
{
    public class Logger
    {
        public static void Log(String str)
        {
            String filename = DateTime.Now.ToString("dd-MM-yyyy") + ".txt";
            string path = AppDomain.CurrentDomain.BaseDirectory + "\\logfile\\" + filename;
            StreamWriter w = File.AppendText(path);
            w.WriteLine(DateTime.Now.ToString() + " " + str);
            w.Close();
        }
    }
}