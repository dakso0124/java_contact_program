package main;

import lib.ContactManager;

/**
 * @�ۼ��� : �����
 * @�ۼ��� : 2020. 12. 21.
 * @filename : ContactMain.java
 * @package : main
 * @desc : ���α׷� ���� ���� Ŭ����
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