package lib;

import exception.ExistNumberException;
import exception.NumberLengthException;
import exception.WrongNumberException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

import java.util.Iterator;

/**
 * @작성자 : 정재겸
 * @작성일 : 2020. 12. 21.
 * @filename : ContactManager.java
 * @package : lib
 * @desc : 연락처 관리를 위한 클래스. 추가, 검색, 수정, 삭제 메소드
 */
public class ContactManager
{
	private HashMap<String, Contact> m_contactMap;		// 연락처 맵
	private Scanner m_sc;
	private ContactFile m_contactFile;
	
	public void Init()
	{
		m_contactFile = new ContactFile();
		m_contactMap = m_contactFile.LoadContactList();
	}

	// 메뉴보기
	public void startManageContactList()
	{
		boolean exit = false;
		String menu = "";

		m_sc = new Scanner(System.in);
		
		while(!exit)
		{
			System.out.println("============================");
			System.out.println("다음 메뉴중 하나를 선택하세요.");
			System.out.println("============================");
			System.out.println("1. 회원 추가");
			System.out.println("2. 회원 목록 보기");
			System.out.println("3. 회원 정보 수정하기");
			System.out.println("4. 회원 삭제");
			System.out.println("5. 종료");
			
			menu = m_sc.nextLine();
			
			switch(menu)
			{
				case "1":
					addContact();
					break;
					
				case "2":
					showContactList();
					break;
					
				case "3":
					editContact();
					break;
					
				case "4":
					deleteContact();
					break;
					
				case "5":
					exit = true;
					break;
					
				default:
					System.out.println("잘못 입력하셨습니다.\n메뉴를 확인 후 다시 입력해 주세요.");
					continue;
			}
		}
		System.out.println("종료되었습니다.");
		m_sc.close();
	}
	
	// 연락처 추가
	private void addContact()
	{
		Contact contact = new Contact();
		
		System.out.println("등록할 회원의 정보를 입력하세요.");
		
		String succesEntry = entryInformation(contact).getName();
		
		m_contactMap.put(contact.getPhoneNumber(), contact);
		
		System.out.println(String.format("%s님이 추가되었습니다.", succesEntry));
		
		m_contactFile.SaveContactList(m_contactMap);
	}
	
	// 연락처보기
	private void showContactList()
	{
		System.out.println(String.format("%d 명의 회원이 저장되어 있습니다.", m_contactMap.size()));
		
		m_contactMap.forEach( (number , contact) ->
		{
			System.out.println(String.format("회원 정보 : 이름 - %s, 전화번호 - %s, 주소 - %s, 타입 - %s", 
					contact.getName(), contact.getPhoneNumber(), contact.getAddress(), contact.getType() ));
		});
	}
	
	// 연락처 수정
	private void editContact()
	{
		String name = null;
		
		while(true)
		{
			System.out.print("수정할 회원의 이름을 입력하세요 : ");
			
			name = m_sc.nextLine();
			
			if(name.isEmpty())
			{
				System.out.println("회원의 이름을 입력해 주세요.");
				continue;
			}
			break;
		}
		
		ArrayList<Contact> findContactList = findContactList(name);
		
		if(findContactList.size() == 0)
		{
			System.out.println("입력한  정보의 회원이 없습니다.");
			return;
		}
		else
		{
			while(true)
			{
				System.out.println(String.format("총 %d개의 항목이 검색되었습니다.\n아래 목록 중 수정할 회원의 번호를 입력하세요.", findContactList.size()));
				
				for(int i = 0 ; i < findContactList.size(); i++)
				{
					System.out.println(String.format("%d. 이름 = %s, 전화번호 = %s, 주소 = %s, 타입 = %s", i + 1,
							findContactList.get(i).getName(), findContactList.get(i).getPhoneNumber(),
							findContactList.get(i).getAddress(), findContactList.get(i).getType()));
				}
				
				String temp = m_sc.nextLine();
				
				try
				{
					int value = Integer.parseInt(temp);
					
					if(value > findContactList.size() || value < 1)
					{
						throw new WrongNumberException();
					}
					else
					{
						entryInformation(findContactList.get(value-1));
					}
				}
				catch (NumberFormatException e)
				{
					System.out.println("잘못 입력하셨습니다.\n메뉴를 확인 후 숫자를 입력해 주세요.");
					continue;
				}
				catch (WrongNumberException e)
				{
					System.out.println("잘못 입력하셨습니다.\n메뉴를 확인 후 다시 입력해 주세요.");
					continue;
				}
				
				break;
			}
		}
		
		m_contactFile.SaveContactList(m_contactMap);
	}
	
	// 연락처 삭제
	private void deleteContact()
	{
		String name = null;
		
		while(true)
		{
			System.out.print("삭제할 회원의 이름을 입력하세요.\n이름 : ");
			name = m_sc.nextLine();
			
			if(name.isEmpty())
			{
				continue;
			}
			break;
		}
		
		ArrayList<Contact> contactList = findContactList(name);

		if(contactList.size() == 0)
		{
			System.out.println("입력한  정보의 회원이 없습니다.");
			return;
		}
		else
		{
			while(true)
			{
				System.out.println(String.format("총 %d개의 항목이 검색되었습니다.\n아래 목록 중 삭제할 회원의 번호를 입력하세요.", contactList.size()));
				
				for(int i = 0 ; i < contactList.size(); i++)
				{
					System.out.println(String.format("%d. 이름 = %s, 전화번호 = %s, 주소 = %s, 타입 = %s", i + 1,
							contactList.get(i).getName(), contactList.get(i).getPhoneNumber(),
							contactList.get(i).getAddress(), contactList.get(i).getType()));
				}
				
				String temp = m_sc.nextLine();
				
				try
				{
					int value = Integer.parseInt(temp);
					
					if(value > contactList.size() || value < 1)
					{
						throw new WrongNumberException();
					}
					else
					{
						System.out.println(String.format("%s님을 삭제했습니다.", 
								m_contactMap.remove(contactList.get(value-1).getPhoneNumber()).getName()));
						
						m_contactFile.SaveContactList(m_contactMap);
					}
				}
				catch (NumberFormatException e)
				{
					System.out.println("잘못 입력하셨습니다.\n메뉴를 확인 후 숫자를 입력해 주세요.");
					continue;
				}
				catch (WrongNumberException e)
				{
					System.out.println("잘못 입력하셨습니다.\n메뉴를 확인 후 다시 입력해 주세요.");
					continue;
				}
				
				break;
			}
		}
	}
	
	// 회원 정보 입력 ( 추가 & 수정 )
	private Contact entryInformation(Contact contact)
	{
		String name = null;
		String number = null;
		String address = null;
		String type = null;
		
		int temp;
		
		while(true)
		{
			System.out.print("이름 : ");
			name = m_sc.nextLine();
			
			if(name.isEmpty())
			{
				System.out.println("이름을 입력해 주세요.");
				continue;
			}
			else if(name.contains(ContactFile.separator))
			{
				System.out.println(String.format("특수문자 \"%s\"를 사용하실수 없습니다. 다시 입력해 주세요.", ContactFile.separator));
				continue;
			}
			
			break;
		}
		
		while(true)
		{
			try
			{
				System.out.print("전화번호 (ex:021234567 or 01012345678 ) : ");
				
				number = m_sc.nextLine();
				temp = Integer.parseInt(number);
				
				if(number.length() < 9 || number.length() > 11)	// ex - 02-123-4567 or 010-1234-5678
				{
					throw new NumberLengthException();
				}
				else if(!number.startsWith("0"))
				{
					throw new WrongNumberException();
				}
				else if(checkExistNumber(number, contact))
				{
					throw new ExistNumberException();
				}
			}
			catch (NumberFormatException e)				// 숫자가 아닌 문자가 포함됨
			{
				System.out.println("잘못 입력하셨습니다.\n숫자로 입력해 주세요.");
				continue;
			}
			catch (NumberLengthException e)				// 9 ~ 11 자리까지만 연락처로 받음
			{
				System.out.println("잘못 입력하셨습니다.\n번호를 다시 한번 확인해 주세요.");
				continue;
			}
			catch (ExistNumberException e)				// 번호 중복
			{
				System.out.println("이미 존재하는 번호입니다.\n다시 한번 확인해 주세요.");
				continue;
			}
			catch (WrongNumberException e)				// 0으로 시작 해야함 ex) 02 010 
			{
				System.out.println("잘못 입력하셨습니다.\n번호를 다시 한번 확인해 주세요.");
				continue;
			}
			
			break;
		}
		
		while(true)
		{
			System.out.print("주소 : ");
			address = m_sc.nextLine();
			
			if(address.isEmpty())
			{
				System.out.println("주소를 입력해 주세요.");
				continue;
			}
			else if(address.contains(ContactFile.separator))
			{
				System.out.println(String.format("특수문자 \'%s\'는 사용하실수 없습니다. 다시 입력해 주세요.", ContactFile.separator));
				continue;
			}			
			
			break;
		}
		
		while(true)
		{
			System.out.print("1. 가족\n2. 친구\n3. 기타\n타입을 선택 하세요 : ");
			
			type = m_sc.nextLine();
			
			try
			{
				temp = Integer.parseInt(type);
				switch(temp)
				{
				case 1:
					type = "가족";
					break;
					
				case 2:
					type = "친구";
					break;
					
				case 3:
					type = "기타";
					break;
					
				default:
					throw new WrongNumberException();
				}
			}
			catch (NumberFormatException e)		// 숫자가 아닌 문자 입력
			{
				System.out.println("잘못 입력하셨습니다.\n메뉴를 확인 후 숫자를 입력해 주세요.");
				continue;
			}
			catch (WrongNumberException e)		// 범위 밖 숫자 입력			
			{
				System.out.println("잘못 입력하셨습니다.\n메뉴를 확인 후 다시 입력해 주세요.");
				continue;
			}
			
			break;
		}
		
		contact.setInformation(name, number, address, type);
		
		return contact;
	}
	
	// 연락처 중복 검사. if true 연락처 중복, if false 연락처 중복x
	private boolean checkExistNumber(String number, Contact contact)
	{		
		Set<String> contactKey = m_contactMap.keySet();
		Iterator<String> iterator = contactKey.iterator();
		
		while(iterator.hasNext())
		{
			Contact temp = m_contactMap.get(iterator.next());

			if(temp.getPhoneNumber().equals(number))				// 전화번호 중복
			{
				if(contact.getPhoneNumber().equals(number))			// 정보 수정으로 들어온 경우 return false
					return false;
				else
					return true;
			}
		}
		
		return false;
	}
	
	// 이름으로 회원 검색
	private ArrayList<Contact> findContactList(String name)
	{
		ArrayList<Contact> contactList = new ArrayList<Contact>();
		Set<String> keySet = m_contactMap.keySet();
		Iterator<String> numIter = keySet.iterator();

		while(numIter.hasNext())
		{
			String numKey = numIter.next();
			
			if(m_contactMap.get(numKey).getName().equals(name))
			{
				contactList.add(m_contactMap.get(numKey));
			}
		}
		
		return contactList;
	}
}