package dnsec.shared.vo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Vo que pode ser utilizado como base
 * @author raguilar
 * */


public class BaseVo implements Serializable{
	public String toString(){
			return ToStringBuilder.reflectionToString(this);
	}
}
