
/** * @author Wael Abouelsaadat */

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class DBApp {

	static Hashtable<String, Table> tablesCreated = new Hashtable<>();

	public DBApp() {

	}

	// this does whatever initialization you would like
	// or leave it empty if there is no code you want to
	// execute at application startup
	public void init() {

	}

	// following method creates one table only
	// strClusteringKeyColumn is the name of the column that will be the primary
	// key and the clustering column as well. The data type of that column will
	// be passed in htblColNameType
	// htblColNameValue will have the column name as key and the data
	// type as value
	public void createTable(String strTableName,
			String strClusteringKeyColumn,
			Hashtable<String, String> htblColNameType) throws DBAppException {

		final String[] allowedDataTypes = { "java.lang.Integer", "java.lang.String", "java.lang.double" };

		// add table entries to meta data
		try {
			FileWriter metaDataWriter = new FileWriter("metadata.csv",true);

			// srt for key values of hashtable
			Set<String> colNames = htblColNameType.keySet();

			// iterator for key values
			Iterator<String> itr = colNames.iterator();

			while (itr.hasNext()) {
				String col = itr.next();
				String dataType = htblColNameType.get(col);
				String entry = "";

				// check if data type is allowed data type
				if (dataType.equals(allowedDataTypes[0]) || dataType.equals(allowedDataTypes[1])
						|| dataType.equals(allowedDataTypes[2])) {
					entry = strTableName + "," + col + "," + dataType + ",";
				} else {
					throw new DBAppException("Invalid Data Type!");
				}

				// checks if column is the clustering column
				if (col.equals(strClusteringKeyColumn)) {
					entry += "True";
				} else {
					entry += "False";
				}

				entry += ",null,null";

				// write entry in csv file
				metaDataWriter.write(entry + "\n");
			}
			metaDataWriter.close();

		} catch (IOException e) {

		}
		tablesCreated.put(strTableName, new Table(strTableName));
		// throw new DBAppException("not implemented yet");
	}

	// following method creates a B+tree index
	public void createIndex(String strTableName,
			String strColName,
			String strIndexName) throws DBAppException, IOException {

		// checks if table exists in metadata file
		if (!Helpers.tableExists(strTableName, strColName)) {
			throw new DBAppException("Table not found in MetaData!");
		}


		Path metaDataPath = Path.of("metadata.csv");
		List<String> fileContent = new ArrayList<>(Files.readAllLines(metaDataPath, StandardCharsets.UTF_8));

		for (int i = 0; i < fileContent.size(); i++) {
			if (fileContent.get(i).contains(strTableName) && (fileContent.get(i).contains(strColName))) {
				String[] newLine = fileContent.get(i).split(",");
				newLine[4] = strIndexName;
				newLine[5] = "B+tree";
				fileContent.set(i, String.join(",", newLine));
				break;
			}
		}
		Files.write(metaDataPath, fileContent, StandardCharsets.UTF_8);

		//get all indexes on insertion table
		ArrayList<String> indexes=null;

		// throw new DBAppException("not implemented yet");
	}

	// following method inserts one row only.
	// htblColNameValue must include a value for the primary key
	public void insertIntoTable(String strTableName, Hashtable<String, Object> htblColNameValue)
			throws DBAppException, IOException {

		// check that table exists
		if (!Helpers.tableExists(strTableName)) {
			throw new DBAppException("Table not found in MetaData!");
		}

		//check that value to enter is valid

		// insert value in page
		// TODO: Check types and validity?
		if(Helpers.insertionValid(strTableName,htblColNameValue)==-2){
			throw new DBAppException("Invalid Data:Inserted Data doesn't match MetaData");
		} else if (Helpers.insertionValid(strTableName,htblColNameValue)==-1) {
			throw new DBAppException("Invalid Data: Primary Key cannot be Null");
		}

		Table tableToInsert = tablesCreated.get(strTableName);
		String clusteringKey = Helpers.getClusteringKey(strTableName);
		Tuple t = new Tuple(htblColNameValue, clusteringKey);
		Page p = tableToInsert.getInsertionPage((Comparable) htblColNameValue.get(clusteringKey), clusteringKey);
		tableToInsert.insert(p, t);

		Hashtable<String, String> createdIndexes = Helpers.getIndexes(strTableName);

		Comparable key;

		// todo:check what to insert
		// update B+trees if available
		for (String index : createdIndexes.keySet()) {
			key = (Comparable) htblColNameValue.get(index);
			// tableToInsert.treesCreated.get(createdIndexes.get(index)).insert(key,);
		}
//		throw new DBAppException("not implemented yet");
	}

	// following method updates one row only
	// htblColNameValue holds the key and new value
	// htblColNameValue will not include clustering key as column name
	// strClusteringKeyValue is the value to look for to find the row to update.
	public void updateTable(String strTableName,
			String strClusteringKeyValue,
			Hashtable<String, Object> htblColNameValue) throws DBAppException {

		throw new DBAppException("not implemented yet");
	}

	// following method could be used to delete one or more rows.
	// htblColNameValue holds the key and value. This will be used in search
	// to identify which rows/tuples to delete.
	// htblColNameValue enteries are ANDED together
	public void deleteFromTable(String strTableName,
			Hashtable<String, Object> htblColNameValue) throws DBAppException {

		throw new DBAppException("not implemented yet");

	}

	public Iterator selectFromTable(SQLTerm[] arrSQLTerms,
			String[] strarrOperators) throws DBAppException {

		return null;
	}

	public static void main(String[] args) {

		try {
			String strTableName = "Student";

			DBApp dbApp = new DBApp();

			Hashtable htblColNameType = new Hashtable();
			htblColNameType.put("id", "java.lang.Integer");
			htblColNameType.put("name", "java.lang.String");
			htblColNameType.put("gpa", "java.lang.double");
			dbApp.createTable(strTableName, "id", htblColNameType);
			htblColNameType.clear();
			
			htblColNameType.put("make","java.lang.String");
			htblColNameType.put("model","java.lang.String");
			htblColNameType.put("year","java.lang.Integer");
			dbApp.createTable("Cars", "make", htblColNameType);


			dbApp.createIndex(strTableName, "gpa", "gpaIndex");
			dbApp.createIndex("Cars", "year", "yearIndex");


			Hashtable<String, Object> htblColNameValue = new Hashtable<String, Object>( );

			//for loop inserts 200 element
			//TODO:mesh 3arf a5ali el pk beh null
			for (int i = 0; i < 400; i++) {
				htblColNameValue.put("id", i+1);
				htblColNameValue.put("name", "Name_" + (i + 2));
				htblColNameValue.put("gpa", Math.random() * 4); // Random GPA between 0 and 4
				dbApp.insertIntoTable(strTableName, htblColNameValue);
			}

			htblColNameValue.put("id",1);
			htblColNameValue.put("name", "Name_" + 1);
			htblColNameValue.put("gpa", Math.random() * 4); // Random GPA between 0 and 4
			dbApp.insertIntoTable(strTableName, htblColNameValue);

			System.out.println(tablesCreated.keys());
			System.out.println(tablesCreated.get("Student").getNumberOfPages());
			System.out.println(Helpers.deserializeTuple("Student_1.ser").size());
			System.out.println(Helpers.deserializeTuple("Student_2.ser").size());
			System.out.println(Helpers.deserializeTuple("Student_3.ser").size());

//			for(Tuple t:Helpers.deserializeTuple("Student_1.ser")){
//				System.out.println(t);
//			}
//			System.out.println("------------------------");
//
//			for(Tuple t:Helpers.deserializeTuple("Student_2.ser")){
//				System.out.println(t);
//			}
			/* Hashtable htblColNameValue = new Hashtable( );
			 * htblColNameValue.put("id", new Integer( 2343432 ));
			 * htblColNameValue.put("name", new String("Ahmed Noor" ) );
			 * htblColNameValue.put("gpa", new Double( 0.95 ) );
			 * dbApp.insertIntoTable( strTableName , htblColNameValue );
			 *
			 * htblColNameValue.clear( );
			 * htblColNameValue.put("id", new Integer( 453455 ));
			 * htblColNameValue.put("name", new String("Ahmed Noor" ) );
			 * htblColNameValue.put("gpa", new Double( 0.95 ) );
			 * dbApp.insertIntoTable( strTableName , htblColNameValue );
			 *
			 * htblColNameValue.clear( );
			 * htblColNameValue.put("id", new Integer( 5674567 ));
			 * htblColNameValue.put("name", new String("Dalia Noor" ) );
			 * htblColNameValue.put("gpa", new Double( 1.25 ) );
			 * dbApp.insertIntoTable( strTableName , htblColNameValue );
			 *
			 * htblColNameValue.clear( );
			 * htblColNameValue.put("id", new Integer( 23498 ));
			 * htblColNameValue.put("name", new String("John Noor" ) );
			 * htblColNameValue.put("gpa", new Double( 1.5 ) );
			 * dbApp.insertIntoTable( strTableName , htblColNameValue );
			 *
			 * htblColNameValue.clear( );
			 * htblColNameValue.put("id", new Integer( 78452 ));
			 * htblColNameValue.put("name", new String("Zaky Noor" ) );
			 * htblColNameValue.put("gpa", new Double( 0.88 ) );
			 * dbApp.insertIntoTable( strTableName , htblColNameValue );
			 *
			/ *
			 * SQLTerm[] arrSQLTerms;
			 * arrSQLTerms = new SQLTerm[2];
			 * arrSQLTerms[0]._strTableName = "Student";
			 * arrSQLTerms[0]._strColumnName= "name";
			 * arrSQLTerms[0]._strOperator = "=";
			 * arrSQLTerms[0]._objValue = "John Noor";
			 * 
			 * arrSQLTerms[1]._strTableName = "Student";
			 * arrSQLTerms[1]._strColumnName= "gpa";
			 * arrSQLTerms[1]._strOperator = "=";
			 * arrSQLTerms[1]._objValue = new Double( 1.5 );
			 * 
			 * String[]strarrOperators = new String[1];
			 * strarrOperators[0] = "OR";
			 * // select * from Student where name = "John Noor" or gpa = 1.5;
			 * Iterator resultSet = dbApp.selectFromTable(arrSQLTerms , strarrOperators);
			 * 
			 */
		}

		catch (Exception exp) {
			exp.printStackTrace();
			System.out.println("Yanhaaari");
		}
	}

}