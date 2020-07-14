package com.ex.app.persistence;

import com.ex.app.models.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SQLPersistence implements Persistable{

    private ConnectionFactory connectionFactory;
    private RR rr;
    private RRs rrs = new RRs();
    private Employee emp;
    private Employees emps = new Employees();
    private Manager man;
    private Managers mans = new Managers();

    public SQLPersistence(ConnectionFactory connectionFactory) {

        try {
            this.connectionFactory = connectionFactory;
            Connection conn = connectionFactory.newConnection();

            String strRRs = "SELECT * FROM RRs";
            PreparedStatement psRRs = conn.prepareStatement(strRRs);
            ResultSet rsRRs = psRRs.executeQuery();

            String strEmps = "SELECT * FROM Employees";
            PreparedStatement psEmps = conn.prepareStatement(strEmps);
            ResultSet rsEmps = psEmps.executeQuery();

            String strEmpRRs = "SELECT * FROM RRs WHERE rrEmpID = ?";
            PreparedStatement psStrEmpRRs = conn.prepareStatement(strEmpRRs);

            String strMans = "SELECT * FROM Managers";
            PreparedStatement psMans = conn.prepareStatement(strMans);
            ResultSet rsMans = psMans.executeQuery();

            while(rsRRs.next()) {

                rr = new RR();
                rr.setId(rsRRs.getInt(1));
                rr.setDate(rsRRs.getDate(2));
                rr.setAmount(rsRRs.getDouble(3));
                rr.setInfo(rsRRs.getString(4));
                String empID = rsRRs.getString(5);
                rr.setEmpID(empID);
                rr.setResolved(rsRRs.getBoolean(6));
                rr.setManID(rsRRs.getString(7));
                rrs.addRR(rr);
            }

            while(rsEmps.next()) {
                emp = new Employee();
                String empID = rsEmps.getString(1);
                emp.setId(empID);
                emp.setPassword(rsEmps.getString(2));
                emp.setEmail(rsEmps.getString(3));
                emp.setName(rsEmps.getString(4));

//                psStrEmpRRs.setInt(1, empID);
//                ResultSet rsEmpRRs = psStrEmpRRs.executeQuery();
//
//                while (rsEmpRRs.next()) {
//                    int i = rsEmpRRs.getInt(1);
//                    emp.getEmpRRs().add(rrGetByID(i));
//                }
                emps.addEmp(emp);
            }

            while(rsMans.next()) {
                man = new Manager();
                man.setManID(rsMans.getString(1));
                man.setPassword(rsMans.getString(2));
                man.setName(rsMans.getString(3));
                mans.addMan(man);
            }

        } catch (Exception ex) {
            System.out.println("Error reading from database");
            ex.printStackTrace();
        }
    }

    @Override
    public List getAllRR() {
        System.out.println(this.rrs);
        return rrs.getRRs();
    }

    @Override
    public List getAllPendingRR() {
        List<RR> pendRRs = new ArrayList<>();

        try {
            Connection conn = connectionFactory.newConnection();
            String query = "SELECT * FROM RRs WHERE rrResolved = false";
            PreparedStatement psQuery = conn.prepareStatement(query);
            ResultSet rsQuery = psQuery.executeQuery();

            while(rsQuery.next()) {
                RR rr = new RR();
                rr.setId(rsQuery.getInt("rrID"));
                rr.setDate(rsQuery.getDate("rrDate"));
                rr.setAmount(rsQuery.getDouble("rrAmount"));
                rr.setInfo(rsQuery.getString("rrInfo"));
                rr.setEmpID(rsQuery.getString("rrEmpID"));
                rr.setResolved(rsQuery.getBoolean("rrResolved"));
                rr.setManID(rsQuery.getString("rrResManID"));
                pendRRs.add(rr);
            }
            return pendRRs;

        } catch (SQLException ex) {
            System.out.println("Error getting all pending RRs");
            ex.printStackTrace();
            return null;
        }

//        for (int i = 0; i < rrs.getRRs().size(); i++) {
//            RR rr = rrs.getRRs().get(i);
//            if (!rr.isResolved()) {
//                pendRRs.add(rr);
//            }
//        }
//        return pendRRs;
    }

    @Override
    public List<RR> getAllResRR() {
        List<RR> resRRs = new ArrayList<>();

        try {
            Connection conn = connectionFactory.newConnection();
            String query = "SELECT * FROM RRs WHERE rrResolved = true";
            PreparedStatement psQuery = conn.prepareStatement(query);
            ResultSet rsQuery = psQuery.executeQuery();

            while(rsQuery.next()) {
                RR rr = new RR();
                rr.setId(rsQuery.getInt("rrID"));
                rr.setDate(rsQuery.getDate("rrDate"));
                rr.setAmount(rsQuery.getDouble("rrAmount"));
                rr.setInfo(rsQuery.getString("rrInfo"));
                rr.setEmpID(rsQuery.getString("rrEmpID"));
                rr.setResolved(rsQuery.getBoolean("rrResolved"));
                rr.setManID(rsQuery.getString("rrResManID"));
                resRRs.add(rr);
            }
            return resRRs;

        } catch (SQLException ex) {
            System.out.println("Error getting all resolved RRs");
            ex.printStackTrace();
            return null;
        }

//        for (int i = 0; i < rrs.getRRs().size(); i++) {
//            RR rr = rrs.getRRs().get(i);
//            if (rr.isResolved()) {
//                resRRs.add(rr);
//            }
//        }
//        return resRRs;
    }

    @Override
    public List getAllEmpRR(String id) {
        List<RR> empRRs = new ArrayList<>();
        Connection conn = connectionFactory.newConnection();

        String query = "SELECT * FROM RRs WHERE rrEmpId = ?";
        try {
            PreparedStatement psQuery = conn.prepareStatement(query);
            psQuery.setString(1, id);
            ResultSet rsQuery = psQuery.executeQuery();

            while(rsQuery.next()) {
                boolean isRes = rsQuery.getBoolean("rrResolved");
                if(!isRes) {
                    RR rr = new RR(rsQuery.getInt("rrID"),rsQuery.getDate("rrDate"),rsQuery.getDouble("rrAmount"),
                            rsQuery.getString("rrInfo"),rsQuery.getString("rrEmpID"),
                            isRes,rsQuery.getString("rrResManID"));
                    empRRs.add(rr);
                }
            }
            return empRRs;
        } catch(SQLException ex) {
            System.out.println("Error retrieving all rrs for one employee");
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public List<RR> getAllEmpResRR(String id) {
        List<RR> empResRRs = new ArrayList<>();
        Connection conn = connectionFactory.newConnection();

        String query = "SELECT * FROM RRs WHERE rrEmpID = ? AND rrResolved = true";
        try {
            PreparedStatement psQuery = conn.prepareStatement(query);
            psQuery.setString(1, id);
            ResultSet rsQuery = psQuery.executeQuery();

            while(rsQuery.next()) {
                int currRR = rsQuery.getInt("rrID");
                empResRRs.add(rrGetByID(currRR));
            }
            return empResRRs;
        } catch(SQLException ex) {
            System.out.println("Error retrieving all resolved rrs for one employee");
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public List getAllEmp() {
        return emps.getEmployees();
    }

    @Override
    public List getAllMan() {
        return mans.getManagers();
    }

    @Override
    public RR rrGetByID(int id) {

        try {
            Connection conn = connectionFactory.newConnection();
            String query = "SELECT * FROM RRs WHERE rrID = ?";
            PreparedStatement psQuery = conn.prepareStatement(query);
            psQuery.setInt(1,id);
            ResultSet rsQuery = psQuery.executeQuery();
            RR rr = null;

            while(rsQuery.next()) {
            rr = new RR(id,rsQuery.getDate("rrDate"),rsQuery.getDouble("rrAmount"),
            rsQuery.getString("rrInfo"),rsQuery.getString("rrEmpID"),
                    rsQuery.getBoolean("rrResolved"),rsQuery.getString("rrResManID"));
            }
            return rr;

        } catch (SQLException ex) {
            System.out.println("Error getting employee by ID");
            ex.printStackTrace();
            return null;
        }

//        for(int i = 0; i < rrs.getRRs().size(); i++) {
//            if(rrs.getRRs().get(i).getId().equals(id)) {
//                return rrs.getRRs().get(i);
//            }
//        }
//        return null;
    }

    @Override
    public Employee empGetByID(String id) {
        try {
            Connection conn = connectionFactory.newConnection();
            String query = "SELECT * FROM Employees WHERE empID = ?";
            PreparedStatement psQuery = conn.prepareStatement(query);
            psQuery.setString(1,id);
            ResultSet rsQuery = psQuery.executeQuery();
            Employee emp = null;
            while(rsQuery.next()) {
                emp = new Employee(id, rsQuery.getString("empPassword"),
                        rsQuery.getString("empName"), rsQuery.getString("empEmail"), null);
            }
            return emp;

        } catch (SQLException ex) {
            System.out.println("Error getting employee by ID");
            ex.printStackTrace();
            return null;
        }

//        for(int i = 0; i < emps.getEmployees().size(); i++) {
//            if(emps.getEmployees().get(i).getEmpId().equals(id)) {
//                return emps.getEmployees().get(i);
//            }
//        }
//        return null;
    }

    @Override
    public Manager manGetByID(String id) {
        try {
            Connection conn = connectionFactory.newConnection();
            String query = "SELECT * FROM Managers WHERE manID = ?";
            PreparedStatement psQuery = conn.prepareStatement(query);
            psQuery.setString(1,id);
            ResultSet rsQuery = psQuery.executeQuery();
            while(rsQuery.next()) {
                Manager man = new Manager(id, rsQuery.getString("manPassword"),
                        rsQuery.getString("manName"));
            }
            return man;

        } catch (SQLException ex) {
            System.out.println("Error getting manager by ID");
            ex.printStackTrace();
            return null;
        }

//        for(int i = 0; i < mans.getManagers().size(); i++) {
//            if(mans.getManagers().get(i).getManID().equals(id)) {
//                return mans.getManagers().get(i);
//            }
//        }
//        return null;
    }

    @Override
    public void addNewRR(RR rr) {
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        rr.setDate(date);
        rr.setResolved(false);
        rrs.addRR(rr);

        try {
            Connection conn = connectionFactory.newConnection();

            String strAddRR = "INSERT INTO RRs (rrDate,rrAmount,rrInfo,rrEmpID,rrResolved)" +
                                                                                " VALUES (?,?,?,?,?)";
            PreparedStatement psAddRR = conn.prepareStatement(strAddRR);
            psAddRR.setDate(1,date);
            psAddRR.setDouble(2,rr.getAmount());
            psAddRR.setString(3,rr.getInfo());
            psAddRR.setString(4,rr.getEmpID());
            psAddRR.setBoolean(5,false);
            psAddRR.execute();

        } catch (Exception ex) {
            System.out.println("Error adding new reimbursement request");
            ex.printStackTrace();
        }
    }

    @Override
    public void addNewEmp(Employee emp) {
        emps.addEmp(emp);

        try {
            Connection conn = connectionFactory.newConnection();

            String strAddEmp = "INSERT INTO Employees (empID,empPassword,empEmail,empName) VALUES (?,?,?,?)";
            PreparedStatement psAddEmp = conn.prepareStatement(strAddEmp);
            psAddEmp.setString(1,emp.getEmpId());
            psAddEmp.setString(2,emp.getPassword());
            psAddEmp.setString(3,emp.getEmail());
            psAddEmp.setString(4,emp.getName());
            psAddEmp.execute();

        } catch (Exception ex) {
            System.out.println("Error adding new employee");
            ex.printStackTrace();
        }
    }

    @Override
    public void addNewMan(Manager man) {
        mans.addMan(man);

        try {
            Connection conn = connectionFactory.newConnection();

            String strAddMan = "INSERT INTO Managers (manID,manPassword,manName) VALUES (?,?,?)";
            PreparedStatement psAddMan = conn.prepareStatement(strAddMan);
            psAddMan.setString(1,man.getManID());
            psAddMan.setString(2,man.getPassword());
            psAddMan.setString(3,man.getName());
            psAddMan.execute();

        } catch (Exception ex) {
            System.out.println("Error adding new manager");
        }
    }

    @Override
    public void updateRR(RR rr) {

        try {
            Connection conn = connectionFactory.newConnection();

            String strUpdateRR = "UPDATE RRs SET rrResolved = ?, rrResManID = ? WHERE rrID = ?";
            PreparedStatement psUpdateRR = conn.prepareStatement(strUpdateRR);
            psUpdateRR.setBoolean(1,rr.isResolved());
            psUpdateRR.setString(2,rr.getManID());
            psUpdateRR.setInt(3,rr.getId());
            psUpdateRR.execute();

        } catch (Exception ex) {
            System.out.println("Error updating reimbursement request");
            ex.printStackTrace();
        }
    }

    @Override
    public void updateEmpEmail(String id, String email) {
        try {
            Connection conn = connectionFactory.newConnection();
            String query = "UPDATE Employees SET empEmail = ? WHERE empID = ?";
            PreparedStatement psQuery = conn.prepareStatement(query);
            psQuery.setString(1, email);
            psQuery.setString(2, id);
            psQuery.execute();

        } catch (SQLException ex) {
            System.out.println("Error updating employee email");
            ex.printStackTrace();
        }
    }

    @Override
    public void updateEmpPassword(String id, String password) {
        try {
            Connection conn = connectionFactory.newConnection();
            String query = "UPDATE Employees SET empPassword = ? WHERE empID = ?";
            PreparedStatement psQuery = conn.prepareStatement(query);
            psQuery.setString(1, password);
            psQuery.setString(2, id);
            psQuery.execute();

        } catch (SQLException ex) {
            System.out.println("Error updating employee password");
            ex.printStackTrace();
        }
    }

    //    @Override
//    public void updateEmp(Employee emp) {
//
//        try {
//            Connection conn = connectionFactory.newConnection();
//
//            String strUpdateEmp = "UPDATE Employees SET empPassword = ?, empEmail = ?, empName = ?" +
//                                                " WHERE empID = ?";
//            PreparedStatement psUpdateEmp = conn.prepareStatement(strUpdateEmp);
//            psUpdateEmp.setString(1,emp.getPassword());
//            psUpdateEmp.setString(2,emp.getEmail());
//            psUpdateEmp.setString(3,emp.getName());
//            psUpdateEmp.setString(4,emp.getEmpId());
//            psUpdateEmp.execute();
//
//        } catch (Exception ex) {
//            System.out.println("Error updating employee");
//            ex.printStackTrace();
//        }
//    }

//    @Override
//    public void updateMan(Manager man) {
//
//        try {
//            Connection conn = connectionFactory.newConnection();
//
//            String strUpdateEmp = "UPDATE Managers SET manPassword = ?, manName = ? WHERE manID = ?";
//            PreparedStatement psUpdateEmp = conn.prepareStatement(strUpdateEmp);
//            psUpdateEmp.setString(1,man.getPassword());
//            psUpdateEmp.setString(2,man.getName());
//            psUpdateEmp.setString(3,man.getManID());
//            psUpdateEmp.execute();
//
//        } catch (Exception ex) {
//            System.out.println("Error updating manager");
//            ex.printStackTrace();
//        }
//    }

    // is this necessary? No
    @Override
    public void deleteRR(RR rr) {

    }

    // is this necessary? No
    @Override
    public void deleteEmp(Employee emp) {

    }

    // is this necessary? No
    @Override
    public void deleteMan(Manager man) {

    }
}
