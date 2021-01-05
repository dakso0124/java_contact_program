package lib;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * @�ۼ��� : �����
 * @�ۼ��� : 2020. 12. 21.
 * @filename : ContactFile.java
 * @package : lib
 * @desc : ����ó�� txt file�� �����ϱ� ���� Ŭ����
 */
public class ContactFile
{
	private String 	m_rootPath	= null;
	private File 	m_file 		= null;
	
	public final static String separator = "|";

// ������, ���� ������Ʈ ��θ� & ����ó ���� ��ġ ����
	public ContactFile()
	{		
		m_file = new File("");
		m_rootPath = m_file.getAbsolutePath() + "\\contact_list.txt";
	}

// file�� ����ó ����Ʈ text�� ����. 
	public void SaveContactList(HashMap<String, Contact> contactList)
	{
		BufferedWriter bw = null;

		try
		{			
			bw = new BufferedWriter(new FileWriter(m_rootPath));

			Set<String> keySet = contactList.keySet();
			Iterator<String> itorator = keySet.iterator();
			
			if(contactList.size() != 0)
			{
				while (itorator.hasNext()) // separator "||" �� �� ���� ����
				{
					Contact contact = contactList.get(itorator.next());

					String info = contact.getName() + separator + contact.getPhoneNumber() + separator + 
					contact.getAddress() + separator + contact.getType();

					bw.write(info);
					bw.newLine();
				}
			}
		}
		catch (IOException e)
		{
			System.out.println("����ó�� �����ϴ� ���� ������ �߻��Ͽ����ϴ�.");
		}
		finally
		{
			try
			{
				if (bw != null)
				{
					bw.close();
				}
			}
			catch (IOException e)
			{
				System.out.println("����ó�� �����ϴ� ���� ������ �߻��Ͽ����ϴ�.");
			}
		}

		System.out.println("����ó�� ������Ʈ �߽��ϴ�.");
	}

	// ����ó ����Ʈ �ҷ�����
	public HashMap<String, Contact> LoadContactList()
	{
		BufferedReader br = null;
		HashMap<String, Contact> contactList = new HashMap<String, Contact>();

		try
		{
			br = new BufferedReader(new FileReader(m_rootPath));
		}
		catch (FileNotFoundException e) // new FileReader
		{
			System.out.println("����ó ������ �����ϴ�.");

			return contactList;
		}

		String line = null;
		try
		{
			while ((line = br.readLine()) != null)
			{
				StringTokenizer st = new StringTokenizer(line, separator);

				String name = st.nextToken();
				String number = st.nextToken();
				String address = st.nextToken();
				String type = st.nextToken();
				
				Contact contact = new Contact(name, number, address, type);
				contactList.put(number, contact);
			}
		}
		catch (IOException e)
		{
			System.out.println("����ó�� �о���� ���� ������ �߻��Ͽ����ϴ�.");

			return contactList;
		}
		finally
		{
			try
			{
				if (br != null)
				{
					br.close();
				}
			}
			catch (IOException e) // br.close()
			{
				System.out.println("����ó�� �о���� ���� ������ �߻��Ͽ����ϴ�. ");
			}
		}

		System.out.println("����ó�� �ҷ����µ� �����߽��ϴ�.");
		return contactList;
	}
}
