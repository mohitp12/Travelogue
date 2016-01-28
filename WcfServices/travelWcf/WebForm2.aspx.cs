// Retrieves bus details between two places specified in arguments

using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Data.Sql;
using System.Data.SqlClient;
using System.Web.UI.WebControls;

namespace travelWcf
{
    public partial class WebForm2 : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            Response.Write("null");
            Service1 sc = new Service1();
            Response.Write(sc.getbusdetail("Ahmedabad", "Ambaji"));		// bus between (source,destination)
            //GridView1.DataSource = adp;
            GridView1.DataBind();
            List<getBus> b;
            try
            {
                b = sc.getbusdetail("Ahmedabad","Ambaji");
                Response.Write(b);
            }
            catch
            {
                Response.Write("error");
            }
        }
    }
}