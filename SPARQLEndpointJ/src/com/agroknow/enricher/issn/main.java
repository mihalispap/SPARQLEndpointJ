package com.agroknow.enricher.issn;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;

public class main {
	
	public static void main( String[] args ) {
		String sparqlQuery;
		
		/*
		 * PREFIX dcterms: <http://purl.org/dc/terms/>
PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>
select distinct ?x 
{

?x rdfs:subClassOf* dbo:PeriodicalLiterature.
} 
*/

		
		
		sparqlQuery=""
				+ "PREFIX dcterms: <http://purl.org/dc/terms/>"
				+ "PREFIX bibo: <http://purl.org/ontology/bibo/>"
				+ "select distinct ?x ?issn ?t "
				+ "	{"
				+ "		?x dcterms:title ?t. 	"
				+ "		OPTIONAL {?x bibo:issn ?issn}"
				+ "		FILTER ( regex (str(?t), \"nd Journal of Agricultural Research\", \"i\") )"
				+ "}";
	
		Query query = QueryFactory.create(sparqlQuery); //s2 = the query above
		//QueryExecution qExe = QueryExecutionFactory.sparqlService( "http://dbpedia.org/sparql", query );
		QueryExecution qExe = QueryExecutionFactory.sparqlService(
					"http://202.45.139.84:10035/catalogs/fao/repositories/jad", query 
				);
		
		
		ResultSet results = qExe.execSelect();
		//ResultSetFormatter.out(System.out, results, query) ;
		
		while(results.hasNext())
	    {
	        QuerySolution sol = results.nextSolution();
	        RDFNode str = sol.get("issn"); 
	        RDFNode thing = sol.get("t"); 

	        System.out.println("ISSN:"+thing+sol.getResource("issn")+sol.toString());
	    }
		
		System.out.println(results.getResultVars().get(0).toString());
		
	}
}






