package databases;

public class QueryBuilder {
	StringBuilder columns;
	StringBuilder conditions;
	String databaseName;
	
	public QueryBuilder(String databaseName) {
		this.databaseName = databaseName;
		this.columns = new StringBuilder("*");
		this.conditions = new StringBuilder();
	}
	
	public QueryBuilder selectColumn(String[] columns) {
		this.columns.setLength(0);
		boolean first = true;
		for(int i = 0; i < columns.length; i++) {
			if(first) {
				this.columns.append(columns[i]);
				first = false;
			}
			else this.columns.append("," + columns[i]);
		}
		return this;
	}
	
	public QueryBuilder setConditions(String[] conditions) {
		boolean first = true;
		for(int i = 0; i < conditions.length; i++) {
			if(first) {
				this.conditions.append("WHERE ");
				this.conditions.append(conditions[i]);
				this.conditions.append(" ");
				first = false;
			}
			else {
				this.conditions.append(" AND ");
				this.conditions.append(conditions[i]);
				this.conditions.append(" ");
			}
		}
		return this;
	}
	
	public String toSQL() {
		StringBuilder output = new StringBuilder("SELECT ");
		output.append(columns.toString());
		output.append(" ");
		output.append("FROM ");
		output.append(databaseName);
		output.append(" ");
		output.append(conditions.toString());
		
		return output.toString();
	}
}
