package com.onlineMIS.ORM.DAO.chainS.sales;

import java.util.ArrayList;
import java.util.List;

public class ChartReportSeries {
		private String name;
		private List<Object> value = new ArrayList<Object>();
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public List<Object> getValue() {
			return value;
		}
		public void setValue(List<Object> value) {
			this.value = value;
		}
}
