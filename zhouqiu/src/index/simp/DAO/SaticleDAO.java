package index.simp.DAO;

import index.simp.DTO.*;

import java.util.ArrayList;
import java.util.List;

public class SaticleDAO {
	/*
	 * �����ҳ������ݴ�DTO���󲢷���List������
	 */
	List list = new ArrayList();
	public   int n = 0;
	
	public void save(String articledto)
	{   n++;
		SarticlecontentDTO dto = new SarticlecontentDTO();//�洢��ҳ����
        dto.setArticlecontent(articledto);
        dto.setArtnuber(n);
       // System.out.println(n);
    	setN(n); //����ҳ��
		list.add(dto);	
		
	}

/*
 * ��¼ҳ��
 */
	public List getList() {
		return list;
	}


	public int getN() {
		return n;
	}


	public void setN(int n) {
		this.n = n;
	}



	
	
	
	 

}
