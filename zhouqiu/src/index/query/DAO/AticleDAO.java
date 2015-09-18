package index.query.DAO;

import index.qurey.DTO.ArticlecontentDTO;

import java.util.ArrayList;
import java.util.List;

public class AticleDAO {
	/*
	 * 将多个页面的内容存DTO对象并放在List对象中
	 */
	List list = new ArrayList();
	public   int n = 0;
	
	public void save(String articledto)
	{   n++;
		ArticlecontentDTO dto = new ArticlecontentDTO();//存储网页内容
        dto.setArticlecontent(articledto);
        dto.setArtnuber(n);
       // System.out.println(n);
    	setN(n); //存总页数
		list.add(dto);	
		
	}

/*
 * 记录页数
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
