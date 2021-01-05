package main;

import lib.ContactManager;

/**
 * @작성자 : 정재겸
 * @작성일 : 2020. 12. 21.
 * @filename : ContactMain.java
 * @package : main
 * @desc : 프로그램 실행 메인 클래스
 */
public class ContactMain
{
	public static void main(String[] args)
	{
		ContactManager mgr = new ContactManager();
		
		mgr.Init();
		mgr.startManageContactList();
		
	}
}