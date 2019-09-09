package dcl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.ActList;
import model.Applicant;
import model.AppliedRoom;
import model.RoomInfo;
import model.TeachingManager;


/*
 * 数据库访问类
 * 
 * 为页面类提供数据来源，使用系统中的模型构造对象返回给调用类
 * */
public class DBHelper {

	//数据库驱动类
	private final static String driverName = "com.mysql.jdbc.Driver";
	//远程数据库地址
	private final static String url = "jdbc:mysql://sanyaya-rdb.mysql.rds.aliyuncs.com:3306/roomapply?useSSL=false";
	//数据库登录用户名
	private final static String user = "sanya_root";
	//数据库登录密码
	private final static String password = "Wang1234";
	static Connection conn;
	
	//创建数据库连接
	private static void Conn() {
		try {
			//加载数据库访问驱动
			Class.forName(driverName).newInstance();
			//创建数据库连接
			conn = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//查询待审核活动列表
	public static List<ActList> ActQuery() throws SQLException {
		
		String sql = "select * from actlist";//SQL语句，查询活动列表
		Conn();//连接数据库
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery(sql);
		
		List<ActList> list = new ArrayList<ActList>();
		ActList act;
		while(rs.next()) {
			//已审核活动不再审核
			if(!rs.getString(6).equals("已提交")) {
				continue;
			}
			act = new ActList();
			act.setActID(rs.getString(1));
			act.setTheme(rs.getString(2));
			act.setContent(rs.getString(3));
			act.setRoomType(rs.getString(4));
			act.setContain(rs.getInt(5));
			act.setState(rs.getString(6));
			act.setRemark(rs.getString(7));
			act.setApplicant(rs.getString(8));
			list.add(act);
		}
		conn.close();//关闭数据库连接
		return list;//返回活动列表
	}
	
	//查询审核通过可以申请教室的活动列表
	public static List<ActList> ActRoom(Applicant stu) throws SQLException {
		
		String sql = "select * from actlist";//SQL语句，查询活动列表
		Conn();//连接数据库
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery(sql);
		
		List<ActList> list = new ArrayList<ActList>();
		ActList act;
		
		while(rs.next()) {
			//审核通过的活动才能申请教室
			if(!rs.getString(6).equals("已批准") || !rs.getString(8).equals(stu.getStuID())) {
				continue;
			}
			
			act = new ActList();
			act.setActID(rs.getString(1));
			act.setTheme(rs.getString(2));
			act.setContent(rs.getString(3));
			act.setRoomType(rs.getString(4));
			act.setContain(rs.getInt(5));
			act.setState(rs.getString(6));
			act.setRemark(rs.getString(7));
			act.setApplicant(rs.getString(8));
			list.add(act);
		}
		conn.close();//关闭数据库连接
		return list;//返回活动列表
	}
	
	//查询教室信息表
		public static List<RoomInfo> RoomInfo() throws SQLException {
			
			String sql = "select * from roominfo";//SQL语句，查询教室信息列表
			Conn();//连接数据库
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(sql);
			
			List<RoomInfo> list = new ArrayList<RoomInfo>();
			RoomInfo room = new RoomInfo();
			while(rs.next()) {
				room = new RoomInfo();
				room.setBuildingID(rs.getString(1));
				room.setRoomNum(rs.getString(2));
				room.setDateNow(rs.getString(3));
				room.setRoomType(rs.getString(4));
				room.setContain(rs.getInt(5));
				room.setTime1(rs.getString(6));
				room.setTime2(rs.getString(7));
				room.setTime3(rs.getString(8));
				room.setTime4(rs.getString(9));
				room.setTime5(rs.getString(10));
				room.setTime6(rs.getString(11));
				room.setTime7(rs.getString(12));
				list.add(room);
			}
			conn.close();//关闭数据库连接
			return list;//返回活动列表
		}
		
	//返回已申请教室列表,不返回已撤销的申请
		public static List<AppliedRoom> AppliedRoom(Applicant stu) throws SQLException {
			
			String sql = "select * from appliedroom";//SQL语句，查询已申请列表列表
			Conn();//连接数据库
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(sql);
			//创建list存储查询到的列表
			List<AppliedRoom> list = new ArrayList<AppliedRoom>();
			AppliedRoom room = new AppliedRoom();
			//读取查询结果
			while(rs.next()) {
				//判断申请是否已撤销
				if(rs.getString(7).equals("0") || !rs.getString(8).equals(stu.getStuID())) {
					continue;
				}
				room = new AppliedRoom();
				room.setId(rs.getInt(1));
				room.setBuildingId(rs.getString(2));
				room.setRoomNum(rs.getString(3));
				room.setApplyDate(rs.getString(4));
				room.setNeedDate(rs.getString(5));
				room.setUseTime(rs.getString(6));
				room.setApplicant(rs.getString(8));
				room.setActId(rs.getString(9));
				list.add(room);
			}
			conn.close();//关闭数据库连接
			return list;//返回活动列表
		}
		
	//获取登录的申请人对象
	public static Applicant getApplicant(String s) throws SQLException {
		//根据申请人的id获取申请人信息
		String sql = "select * from applicant where stuid = '";
		sql = sql + s + "'";
		//连接数据库
		Conn();
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery(sql);
		//创建一个申请人对象存储申请人信息
		Applicant stu = new Applicant();
		//读取查询结果
		while(rs.next()) {
			stu.setStuID(rs.getString(1));
			stu.setStuName(rs.getString(2));
			stu.setStuClass(rs.getString(3));
			stu.setStuCollege(rs.getString(4));
			stu.setEmail(rs.getString(5));
			stu.setPhone(rs.getString(6));
			stu.setPassWord(rs.getString(7));
		}
		conn.close();//关闭数据库连接
		return stu;
	}
	
	//获取登录的教务管理员对象
	public static TeachingManager getManager(String s) throws SQLException {
		//根据申请人的id获取申请人信息
		String sql = "select * from teachingmanager where employeeid = '";
		sql = sql + s + "'";
		//连接数据库
		Conn();
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery(sql);
		//创建一个申请人对象存储申请人信息
		TeachingManager teach = new TeachingManager();
		//读取查询结果
		while(rs.next()) {
			teach.setEmployeeID(rs.getString(1));
			teach.setEmployeeName(rs.getString(2));
			teach.setWorkArea(rs.getString(3));
			teach.setEmail(rs.getString(4));
			teach.setPhone(rs.getString(5));
			teach.setPassWord(rs.getString(6));
		}
		conn.close();//关闭数据库连接
		return teach;
	}
	
	//获取教务管理人员邮箱
	public static String getEmail() throws SQLException {
		// 拼接查询语句
		String sql = "select email from teachingmanager";

		//连接数据库
		Conn();
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery(sql);
		
		String email = null;//获取教务管理人员邮箱
		//读取查询结果
		while(rs.next()) {
			email = rs.getString(1);
		}
		conn.close();//关闭数据库连接
		return email;
	}
	
	//获取申请人邮箱地址
	public static String getApplyEmail(String s) throws SQLException {
		// 拼接查询语句
		String sql = "select email from applicant where stuid='";
		sql = sql + s + "'";
		
		//连接数据库
		Conn();
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery(sql);
		
		String email = null;//获取申请人邮箱
		
		//读取查询结果
		while(rs.next()) {
			email = rs.getString(1);
		}
		
		conn.close();//关闭数据库连接
		return email;
	}
	
	//获取楼栋管理员邮箱地址
	public static String getBuildEmail(String s) throws SQLException {
		// 拼接查询语句
		String sql = "select email from buildmanager where workbuild='";
		sql = sql + s + "'";
		
		//连接数据库
		Conn();
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery(sql);
		
		String email = null;//获取申请人邮箱
		
		//读取查询结果
		while(rs.next()) {
			email = rs.getString(1);
		}
		
		conn.close();//关闭数据库连接
		return email;
	}
	
	//更新教室空闲信息
	public static void UpdateRoom(String buildingid, String roomnum, String date, String time) throws SQLException {
		//拼接更新语句
		String sql = "update roominfo set "+time+"='1' where buildingid='"+buildingid+
				"' and roomnum ='"+roomnum+"' and date='"+date+"'";
		Conn();//连接数据库
		Statement stat = conn.createStatement();
		stat.executeUpdate(sql);//执行更新
	}
	
	//向教室申请表插入一条记录
	public static void UpdateApplied(String buildingid, String roomnum, String date, String time, String stuid, String actid) 
			throws SQLException {
		//获取系统的当前日期
		Date dateNow = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		//拼接更新语句
		String sql = "insert into appliedroom(buildingid, roomnum, applydate, needdate, appliedtime, applicant, actid) values('"+
				buildingid+"','"+roomnum+"','"+sdf.format(dateNow)+"','"+date+"','"+time+"','"+stuid+"','"+actid+"')";
		
		String s = "update actlist set state = '已申请'  where actid ='"+actid+"'";
		
		Conn();//连接数据库
		Statement stat = conn.createStatement();
		stat.executeUpdate(sql);//执行插入
		stat.executeUpdate(s);//更新活动状态
	}
	
	public static ResultSet Query(String s) throws SQLException {
		
		Conn();//连接数据库
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery(s);
		return rs;
	}
	
	//向活动列表插入一条记录
	public static void UpdateList(String theme, String content, String roomType, String contain, String stuid) 
			throws SQLException {
		//拼接更新语句
		String sql = "insert into actlist(theme, content, roomtype, contain, state, applicant) values('"
				+theme+"','" +content+"', '"+roomType+"', "+contain+",'已提交','"+ stuid + "')";
		Conn();//连接数据库
		Statement stat = conn.createStatement();
		stat.executeUpdate(sql);
	}
	
	//向活动列表添加审核结果并设置活动状态
	public static void UpdateState(String remark, String check, String employeeid, String actid) throws SQLException {
		//拼接更新语句
		String sql = "UPDATE actlist SET remark = '"+remark+"',state='"+check+"',reviewer='"+
				employeeid+"'WHERE ActId = '"+actid+"'";
		
		Conn();//连接数据库
		Statement stat = conn.createStatement();
		stat.executeUpdate(sql);
	}
	
	//更改个人信息
	public static void ChangeInfo(String email, String phone, String table, String type, String id) throws SQLException {
		//拼接更新语句
		String sql = "update "+table+" set email = '"+email+"',phone='"+phone+"' where "+type+"='"+id+"'";

		Conn();//连接数据库
		Statement stat = conn.createStatement();
		stat.executeUpdate(sql);
		
	}
	
	//更改密码
	public static void ChangeInfo(String pwd, String table, String type, String id) throws SQLException {
		//拼接更新语句
		String sql = "update "+table+" set password='"+pwd+"' where "+type+"='"+id+"'";
		
		Conn();//连接数据库
		Statement stat = conn.createStatement();
		stat.executeUpdate(sql);
		
	}
	
	//撤销申请后更改教室信息表
	public static void UndoRoom(String buildingid, String roomnum, String date, String time) throws SQLException {
		//拼接更新语句
		String sql = "update roominfo set "+time+"='0' where buildingid='"+buildingid+
				"' and roomnum ='"+roomnum+"' and date='"+date+"'";
		Conn();//连接数据库
		Statement stat = conn.createStatement();
		stat.executeUpdate(sql);//执行更新
	}
	
	//撤销申请后更新已申请教室表
	public static void UndoApplied(int id, String actid) 
			throws SQLException {
		//获取系统的当前日期
		Date dateNow = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		//拼接更新语句
		String sql = "update appliedroom set state = '0' where id ="+id;

		String s = "update actlist set state = '已撤销'  where actid ='"+actid+"'";
		
		Conn();//连接数据库
		Statement stat = conn.createStatement();
		stat.executeUpdate(sql);//执行插入
		stat.executeUpdate(s);//更新活动状态
	}
}
