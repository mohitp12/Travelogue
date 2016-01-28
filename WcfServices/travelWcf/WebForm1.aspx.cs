// Tests the webservice to retreive informations of place specified as arguments
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace travelWcf
{
    public partial class WebForm1 : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            Service1 s = new Service1();
          //  getPlace g = new getPlace();
            List<model1> a;
            try
            {
                a = s.getinfo("Ambaji");	//destination place
                Response.Write(a);
            }
            catch
            {
                Response.Write("error");
            }
        }
    }
}