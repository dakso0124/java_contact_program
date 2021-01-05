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
 * @작성자 : 정재겸
 * @작성일 : 2020. 12. 21.
 * @filename : ContactFile.java
 * @package : lib
 * @desc : 연락처를 txt file로 저장하기 위한 클래스
 */
public class ContactFile
{
	private String 	m_rootPath	= null;
	private File 	m_file 		= null;
	
	public final static String separator = "|";

// 생성자, 현제 프로젝트 경로를 & 연락처 파일 위치 지정
	public ContactFile()
	{		
		m_file = new File("");
		m_rootPath = m_file.getAbsolutePath() + "\\contact_list.txt";
	}

// file에 연락처 리스트 text로 저장. 
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
				while (itorator.hasNext()) // separator "||" 로 각 변수 구분
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
			System.out.println("연락처를 저장하는 도중 문제가 발생하였습니다.");
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
				System.out.println("연락처를 저장하는 도중 문제가 발생하였습니다.");
			}
		}

		System.out.println("연락처를 업데이트 했습니다.");
	}

	// 연락처 리스트 불러오기
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
			System.out.println("연락처 파일이 없습니다.");

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
			System.out.println("연락처를 읽어오는 도중 문제가 발생하였습니다.");

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
				System.out.println("연락처를 읽어오는 도중 문제가 발생하였습니다. ");
			}
		}

		System.out.println("연락처를 불러오는데 성공했습니다.");
		return contactList;
	}
}
