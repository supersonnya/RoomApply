package model;

public class AppliedRoom {
	//已申请教室表
	private int Id;
	private String BuildingId;
	private String RoomNum;
	private String applyDate;
	private String needDate;
	private String useTime;
	private String applicant;
	private String actId;
	//get和set方法
	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getBuildingId() {
		return BuildingId;
	}

	public void setBuildingId(String buildingId) {
		BuildingId = buildingId;
	}

	public String getRoomNum() {
		return RoomNum;
	}

	public void setRoomNum(String roomNum) {
		RoomNum = roomNum;
	}

	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public String getNeedDate() {
		return needDate;
	}

	public void setNeedDate(String needDate) {
		this.needDate = needDate;
	}

	public String getUseTime() {
		return useTime;
	}

	public void setUseTime(String useTime) {
		this.useTime = useTime;
	}

	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}

	public String getActId() {
		return actId;
	}

	public void setActId(String actId) {
		this.actId = actId;
	}

	//构造方法
	public AppliedRoom() {
		// TODO Auto-generated constructor stub
	}
}
