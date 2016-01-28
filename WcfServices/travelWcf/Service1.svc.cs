//Establishes connection with database and retrieves data with different validations.

using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;
using System.Data.SqlClient;
using System.Data;
using System.IO;
namespace travelWcf
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "Service1" in code, svc and config file together.
    public class Service1 : IService1
    {
        public string GetData(int value)
        {
            return string.Format("You entered: {0}", value);
        }

        public CompositeType GetDataUsingDataContract(CompositeType composite)
        {
            if (composite == null)
            {
                throw new ArgumentNullException("composite");
            }
            if (composite.BoolValue)
            {
                composite.StringValue += "Suffix";
            }
            return composite;
        }
       

        public List<getPlace> getallplace(string cityName)
        {
            try
            {
                List<getPlace> list = new List<getPlace>();
                SqlConnection con = new SqlConnection();
                con.ConnectionString = @"Data Source=MOHIT-PC\SQLEXPRESS;Initial Catalog=travelogue;User ID=sa;Password=patel";
                string query = "select p.place_name from Place_Detail as p where p.CT_id = (select c.CT_id From City_Detail as c where c.CT_Name = @CityName) ";
                SqlCommand cmd = new SqlCommand(query, con);
                SqlParameter p = new SqlParameter("@CityName", cityName);
                cmd.Parameters.Add(p);
                con.Open();
                SqlDataAdapter adp = new SqlDataAdapter();
                DataTable dt = new DataTable();
                adp.SelectCommand = cmd;
                adp.Fill(dt);
                foreach (DataRow dr in dt.Rows)
                {
                    getPlace obj = new getPlace();
                    obj.CPlace = dr["Place_Name"].ToString();
                    list.Add(obj);
                }
                return list;
             
            }
            catch (Exception e)
            {
                return new List<getPlace>();
            }
          
        }

        public List<model1> getinfo(string placeName)
        {
            SqlConnection con = new SqlConnection();
            List<model1> list = new List<model1>();
            con.ConnectionString = @"Data Source=MOHIT-PC\SQLEXPRESS;Initial Catalog=travelogue;User ID=sa;Password=patel";
            string query = "select intro,lat,long from Place_Detail where Place_Name = @PlaceName";
            SqlCommand cmd = new SqlCommand(query, con);
            SqlParameter p = new SqlParameter("@PlaceName", placeName);
            cmd.Parameters.Add(p);
            con.Open();
            SqlDataAdapter adp = new SqlDataAdapter();
            DataTable dt = new DataTable();
            adp.SelectCommand = cmd;
            adp.Fill(dt);

            foreach (DataRow dr in dt.Rows)
            {
                model1 m = new model1();
                m.intro = dt.Rows[0]["intro"].ToString();
                m.lat = dt.Rows[0]["lat"].ToString();
                m.longi = dt.Rows[0]["long"].ToString();
                list.Add(m);                
            }
            con.Close();
            return list;
        }

        public List<getBus> getbusdetail(string Source, string Destination)
        {
            Logger.Log(Source + " " + Destination);  
            try
            {
                SqlConnection con = new SqlConnection();
                List<getBus> list = new List<getBus>();
                con.ConnectionString = @"Data Source=MOHIT-PC\SQLEXPRESS;Initial Catalog=travelogue;User ID=sa;Password=patel";
                string query = "select Source,Via,Destination,Time from Bus_Detail1 where (Source=@Source OR Via=@Source) AND (Via=@Destination OR Destination=@Destination)";
                SqlCommand cmd = new SqlCommand(query, con);
                SqlParameter p = new SqlParameter("@Source", Source);
                cmd.Parameters.Add(p);
                SqlParameter p1 = new SqlParameter("@Destination", Destination);
                cmd.Parameters.Add(p1);
                con.Open();
                SqlDataAdapter adp = new SqlDataAdapter();
                DataTable dt = new DataTable();
                adp.SelectCommand = cmd;
                adp.Fill(dt);
                foreach (DataRow dr in dt.Rows)
                {                 
                    getBus b = new getBus();
                    b.source = dr["Source"].ToString();
                    b.via = dr["Via"].ToString();
                    b.destination = dr["Destination"].ToString();
                    b.time = dr["Time"].ToString();
                    list.Add(b);  
                }
                con.Close();
                Logger.Log("result");
                return list;
                
            }
            catch (Exception e)
            {
                Logger.Log(e.ToString());  
                return new List<getBus>();
            }
        }
    }
}
