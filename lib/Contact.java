package lib;

/**
 * @작성자 : 정재겸
 * @작성일 : 2020. 12. 21.
 * @filename : Contact.java
 * @package : lib
 * @desc : 연락처 클래스
 */
public class Contact
{
	private String m_name;
	private String m_phoneNumber;
	private String m_address;
	private String m_type;
	
	public Contact()
	{
		this.m_name 			= "";
		this.m_phoneNumber	= "";
		this.m_address		= "";
		this.m_type			= "";
	}
	
	public Contact(String name, String phoneNum, String address, String type)
	{
		this.m_name = name;
		this.m_phoneNumber = phoneNum;
		this.m_address = address;
		this.m_type = type;
	}
	
	public void setInformation(String name, String phoneNumber, String address, String type)
	{
		this.m_name = name;
		this.m_phoneNumber = phoneNumber;
		this.m_address = address;
		this.m_type = type;
	}

	public String getName()
	{
		return m_name;
	}

	public String getPhoneNumber()
	{
		return m_phoneNumber;
	}

	public String getAddress()
	{
		return m_address;
	}

	public String getType()
	{
		return m_type;
	}	
}