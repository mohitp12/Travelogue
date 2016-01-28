// Communicates with the GCM connection server which in-turn communicates with the android device passed as argument 

using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Net;
using System.IO;
namespace SendPushInAndroid
{
    public partial class _Default : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)	//Unique Device key to which push notification is to be sent.
        {
            Notify("");
        }
        public void Notify(string regId)
        {
            var applicationID = "";// secret API key 
            var result = "";
            //  var SENDER_ID = "12 digit id you will get from google account";
            var httpWebRequest = (HttpWebRequest)WebRequest.Create("https://android.googleapis.com/gcm/send");
            httpWebRequest.ContentType = "application/json";
            httpWebRequest.Method = "POST";
            httpWebRequest.Headers.Add(string.Format("Authorization: key={0}", applicationID));
            //  httpWebRequest.Headers.Add(string.Format("Sender: key={0}", SENDER_ID));
            String a = "";		//String to be displayed in the notification.
            using (var streamWriter = new StreamWriter(httpWebRequest.GetRequestStream()))
            {
                string json = "{\"registration_ids\":[\"" + regId + "\"]," + "\"data\": { \"price\" : \""+a+"\"}}";
                Console.WriteLine(json);
                streamWriter.Write(json);
                streamWriter.Flush();
                streamWriter.Close();

                var httpResponse = (HttpWebResponse)httpWebRequest.GetResponse();
                using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
                {
                    result = streamReader.ReadToEnd();
                }
            }

        }

    }
}
