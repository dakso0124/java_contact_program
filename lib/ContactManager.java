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
 * @�ۼ��� : �����
 * @�ۼ��� : 2020. 12. 21.
 * @filename : ContactManager.java
 * @package : lib
 * @desc : ����ó ������ ���� Ŭ����. �߰�, �˻�, ����, ���� �޼ҵ�
 */
public class ContactManager
{
	private HashMap<String, Contact> m_contactMap;		// ����ó ��
	private Scanner m_sc;
	private ContactFile m_contactFile;
	
	public void Init()
	{
		m_contactFile = new ContactFile();
		m_contactMap = m_contactFile.LoadContactList();
	}

	// �޴�����
	public void startManageContactList()
	{
		boolean exit = false;
		String menu = "";

		m_sc = new Scanner(System.in);
		
		while(!exit)
		{
			System.out.println("============================");
			System.out.println("���� �޴��� �ϳ��� �����ϼ���.");
			System.out.println("============================");
			System.out.println("1. ȸ�� �߰�");
			System.out.println("2. ȸ�� ��� ����");
			System.out.println("3. ȸ�� ���� �����ϱ�");
			System.out.println("4. ȸ�� ����");
			System.out.println("5. ����");
			
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
					System.out.println("�߸� �Է��ϼ̽��ϴ�.\n�޴��� Ȯ�� �� �ٽ� �Է��� �ּ���.");
					continue;
			}
		}
		System.out.println("����Ǿ����ϴ�.");
		m_sc.close();
	}
	
	// ����ó �߰�
	private void addContact()
	{
		Contact contact = new Contact();
		
		System.out.println("����� ȸ���� ������ �Է��ϼ���.");
		
		String succesEntry = entryInformation(contact).getName();
		
		m_contactMap.put(contact.getPhoneNumber(), contact);
		
		System.out.println(String.format("%s���� �߰��Ǿ����ϴ�.", succesEntry));
		
		m_contactFile.SaveContactList(m_contactMap);
	}
	
	// ����ó����
	private void showContactList()
	{
		System.out.println(String.format("%d ���� ȸ���� ����Ǿ� �ֽ��ϴ�.", m_contactMap.size()));
		
		m_contactMap.forEach( (number , contact) ->
		{
			System.out.println(String.format("ȸ�� ���� : �̸� - %s, ��ȭ��ȣ - %s, �ּ� - %s, Ÿ�� - %s", 
					contact.getName(), contact.getPhoneNumber(), contact.getAddress(), contact.getType() ));
		});
	}
	
	// ����ó ����
	private void editContact()
	{
		String name = null;
		
		while(true)
		{
			System.out.print("������ ȸ���� �̸��� �Է��ϼ��� : ");
			
			name = m_sc.nextLine();
			
			if(name.isEmpty())
			{
				System.out.println("ȸ���� �̸��� �Է��� �ּ���.");
				continue;
			}
			break;
		}
		
		ArrayList<Contact> findContactList = findContactList(name);
		
		if(findContactList.size() == 0)
		{
			System.out.println("�Է���  ������ ȸ���� �����ϴ�.");
			return;
		}
		else
		{
			while(true)
			{
				System.out.println(String.format("�� %d���� �׸��� �˻��Ǿ����ϴ�.\n�Ʒ� ��� �� ������ ȸ���� ��ȣ�� �Է��ϼ���.", findContactList.size()));
				
				for(int i = 0 ; i < findContactList.size(); i++)
				{
					System.out.println(String.format("%d. �̸� = %s, ��ȭ��ȣ = %s, �ּ� = %s, Ÿ�� = %s", i + 1,
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
					System.out.println("�߸� �Է��ϼ̽��ϴ�.\n�޴��� Ȯ�� �� ���ڸ� �Է��� �ּ���.");
					continue;
				}
				catch (WrongNumberException e)
				{
					System.out.println("�߸� �Է��ϼ̽��ϴ�.\n�޴��� Ȯ�� �� �ٽ� �Է��� �ּ���.");
					continue;
				}
				
				break;
			}
		}
		
		m_contactFile.SaveContactList(m_contactMap);
	}
	
	// ����ó ����
	private void deleteContact()
	{
		String name = null;
		
		while(true)
		{
			System.out.print("������ ȸ���� �̸��� �Է��ϼ���.\n�̸� : ");
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
			System.out.println("�Է���  ������ ȸ���� �����ϴ�.");
			return;
		}
		else
		{
			while(true)
			{
				System.out.println(String.format("�� %d���� �׸��� �˻��Ǿ����ϴ�.\n�Ʒ� ��� �� ������ ȸ���� ��ȣ�� �Է��ϼ���.", contactList.size()));
				
				for(int i = 0 ; i < contactList.size(); i++)
				{
					System.out.println(String.format("%d. �̸� = %s, ��ȭ��ȣ = %s, �ּ� = %s, Ÿ�� = %s", i + 1,
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
						System.out.println(String.format("%s���� �����߽��ϴ�.", 
								m_contactMap.remove(contactList.get(value-1).getPhoneNumber()).getName()));
						
						m_contactFile.SaveContactList(m_contactMap);
					}
				}
				catch (NumberFormatException e)
				{
					System.out.println("�߸� �Է��ϼ̽��ϴ�.\n�޴��� Ȯ�� �� ���ڸ� �Է��� �ּ���.");
					continue;
				}
				catch (WrongNumberException e)
				{
					System.out.println("�߸� �Է��ϼ̽��ϴ�.\n�޴��� Ȯ�� �� �ٽ� �Է��� �ּ���.");
					continue;
				}
				
				break;
			}
		}
	}
	
	// ȸ�� ���� �Է� ( �߰� & ���� )
	private Contact entryInformation(Contact contact)
	{
		String name = null;
		String number = null;
		String address = null;
		String type = null;
		
		int temp;
		
		while(true)
		{
			System.out.print("�̸� : ");
			name = m_sc.nextLine();
			
			if(name.isEmpty())
			{
				System.out.println("�̸��� �Է��� �ּ���.");
				continue;
			}
			else if(name.contains(ContactFile.separator))
			{
				System.out.println(String.format("Ư������ \"%s\"�� ����ϽǼ� �����ϴ�. �ٽ� �Է��� �ּ���.", ContactFile.separator));
				continue;
			}
			
			break;
		}
		
		while(true)
		{
			try
			{
				System.out.print("��ȭ��ȣ (ex:021234567 or 01012345678 ) : ");
				
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
			catch (NumberFormatException e)				// ���ڰ� �ƴ� ���ڰ� ���Ե�
			{
				System.out.println("�߸� �Է��ϼ̽��ϴ�.\n���ڷ� �Է��� �ּ���.");
				continue;
			}
			catch (NumberLengthException e)				// 9 ~ 11 �ڸ������� ����ó�� ����
			{
				System.out.println("�߸� �Է��ϼ̽��ϴ�.\n��ȣ�� �ٽ� �ѹ� Ȯ���� �ּ���.");
				continue;
			}
			catch (ExistNumberException e)				// ��ȣ �ߺ�
			{
				System.out.println("�̹� �����ϴ� ��ȣ�Դϴ�.\n�ٽ� �ѹ� Ȯ���� �ּ���.");
				continue;
			}
			catch (WrongNumberException e)				// 0���� ���� �ؾ��� ex) 02 010 
			{
				System.out.println("�߸� �Է��ϼ̽��ϴ�.\n��ȣ�� �ٽ� �ѹ� Ȯ���� �ּ���.");
				continue;
			}
			
			break;
		}
		
		while(true)
		{
			System.out.print("�ּ� : ");
			address = m_sc.nextLine();
			
			if(address.isEmpty())
			{
				System.out.println("�ּҸ� �Է��� �ּ���.");
				continue;
			}
			else if(address.contains(ContactFile.separator))
			{
				System.out.println(String.format("Ư������ \'%s\'�� ����ϽǼ� �����ϴ�. �ٽ� �Է��� �ּ���.", ContactFile.separator));
				continue;
			}			
			
			break;
		}
		
		while(true)
		{
			System.out.print("1. ����\n2. ģ��\n3. ��Ÿ\nŸ���� ���� �ϼ��� : ");
			
			type = m_sc.nextLine();
			
			try
			{
				temp = Integer.parseInt(type);
				switch(temp)
				{
				case 1:
					type = "����";
					break;
					
				case 2:
					type = "ģ��";
					break;
					
				case 3:
					type = "��Ÿ";
					break;
					
				default:
					throw new WrongNumberException();
				}
			}
			catch (NumberFormatException e)		// ���ڰ� �ƴ� ���� �Է�
			{
				System.out.println("�߸� �Է��ϼ̽��ϴ�.\n�޴��� Ȯ�� �� ���ڸ� �Է��� �ּ���.");
				continue;
			}
			catch (WrongNumberException e)		// ���� �� ���� �Է�			
			{
				System.out.println("�߸� �Է��ϼ̽��ϴ�.\n�޴��� Ȯ�� �� �ٽ� �Է��� �ּ���.");
				continue;
			}
			
			break;
		}
		
		contact.setInformation(name, number, address, type);
		
		return contact;
	}
	
	// ����ó �ߺ� �˻�. if true ����ó �ߺ�, if false ����ó �ߺ�x
	private boolean checkExistNumber(String number, Contact contact)
	{		
		Set<String> contactKey = m_contactMap.keySet();
		Iterator<String> iterator = contactKey.iterator();
		
		while(iterator.hasNext())
		{
			Contact temp = m_contactMap.get(iterator.next());

			if(temp.getPhoneNumber().equals(number))				// ��ȭ��ȣ �ߺ�
			{
				if(contact.getPhoneNumber().equals(number))			// ���� �������� ���� ��� return false
					return false;
				else
					return true;
			}
		}
		
		return false;
	}
	
	// �̸����� ȸ�� �˻�
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