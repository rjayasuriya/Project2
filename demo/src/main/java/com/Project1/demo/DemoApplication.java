package com.Project1.demo;


import com.Project1.demo.aapl.call1.Call1;
import com.Project1.demo.aapl.call1.Call1Repository;
import com.Project1.demo.aapl.call1.Call1Service;
import com.Project1.demo.aapl.put1.Put1;
import com.Project1.demo.aapl.put1.Put1Repository;
import com.Project1.demo.aapl.put1.Put1Service;
import com.Project1.demo.amd.call1.AMDCall1;
import com.Project1.demo.amd.call1.AMDCall1Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;



/*
* SPring setup
* 	Dependencies: Spring Web, Spring Data JPA, PostgreSQL Driver, lombok
* 	2.7.7
* 	Maven
*
*
*
* */


@RestController
//@SpringBootApplication
//@ComponentScan(basePackages="{com.Project1.demo}")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
//public class DemoApplication {
public class DemoApplication implements CommandLineRunner {

	@Autowired
	public Call1Repository call1Repo;

	@Autowired
	public Put1Repository put1Repo;

	@Autowired
	public AMDCall1Repository AMDCall1Repo;



	@GetMapping("/getAll")
	public List<Call1> getCalls(){
		return call1Repo.findAll();
	}


	@Override
	public void run(String... args) throws Exception{

		// test inserting into table: call1: Success
		Call1 one = new Call1();
		one.setAsk(1);
		one.setBid(2);
		call1Repo.saveAll(Arrays.asList(one));


		// test inserting into table: put1: Success
		Put1 put1 = new Put1();
		put1.setAsk(87);
		put1.setBid(77);
		put1Repo.save(put1);

		// Testing adding data to database: amd: PASS
		AMDCall1 amdcall = new AMDCall1();
		amdcall.setAsk(809);
		AMDCall1Repo.save(amdcall);





	}





	public static void main(String[] args) throws Exception{
		//SpringApplication.run(DemoApplication.class, args);

		// when creating new database config classes, we have to create the database manually
		// how to create database:
		// log into as user postgres: psql postgres -U postgres
		// create database: CREATE DATABASE databasename;
		// view databases: \l
		// grant access to user: postgres: GRANT ALL PRIVILEGES ON DATABASE databasename TO postgres; postgres=> \list
		// connect to database: \connect databaseName
		// view fields in table: \d tableName
		// get all data from table: select * FROM tableName;



		// START: here we have the implementation to POST a call1 to the table
		ConfigurableApplicationContext applicationContext = SpringApplication.run(DemoApplication.class, args);
		Call1 c1 = new Call1();
		c1.setAsk(976);
		c1.setBid(909);


		Call1Service service = applicationContext.getBean(Call1Service.class);
		Put1Service servicePut = applicationContext.getBean(Put1Service.class);

		service.saveOrUpdate(c1);


		// now what we need to do is to make sure the set methods exist for each column

		// STOP




		//context.getBean(DemoApplication.class).call1Repo.save(new Call1());

		// multidatabase tutorial: https://www.youtube.com/watch?v=nzszxQbQ5WU&ab_channel=NareshiTechnologies

		// MAJOR CHANGE: IN POSTMAN IN PROXY IT WAS USING: USE SYSTEM PROXY AND THE CHECKBOX FOR: RESPECT HTTP... WAS NOT CHECKED


		//API Website: https://eodhistoricaldata.com

		// user: postgres, pass: 123




		/*
		* If you want to test inputting data use postman
		* https://www.javatpoint.com/spring-boot-crud-operations
		*
		* */







		String address = "http://localhost:8080/aapl/call1";
		WebClient webClient = WebClient.create(address);

		String ipAddress = "127.0.0.1";

		InetAddress inet = InetAddress.getByName(ipAddress);

		System.out.println("Sending Ping Request to " + ipAddress);
		System.out.println(inet.isReachable(5000) ? "Host is reachable" : "Host is NOT reachable");




		/*
		* How to check what is running on ports:
		* 	lsof -i :portNumber
		*
		* stop process: kill -9 PID
		* */

 		/*
		RestTemplate restTemplate = new RestTemplate();
		String fooResourceUrl
				= "https://eodhistoricaldata.com/api/search/Apple Inc?api_token=63af4b148fbb43.82883652&limit=1";
		ResponseEntity<String> response4
				= restTemplate.getForEntity(fooResourceUrl, String.class);
		String body = response4.getBody();
		System.out.println("97. response: "+body);
		*/
		// STOP TEST

		//API TEST GET SPECIFIC OPTION
		RestTemplate restTemplate2 = new RestTemplate();
		String fooResourceUrl2
				= "https://eodhistoricaldata.com/api/options/AAPL?contract_name=AAPL180420P00002500&api_token=demo&from=2000-01-01";
		ResponseEntity<Object> response5
				= restTemplate2.getForEntity(fooResourceUrl2, Object.class);
		Object  body = response5.getBody();
		LinkedHashMap<Object, Object> numbers = (LinkedHashMap<Object, Object>) body;
		numbers.keySet();






		System.out.println("108. response: "+body.toString()+"| type: "+body.getClass());
		System.out.println("---------------------------------------------------");
		for(Object i: numbers.keySet()){
			if(i.toString().equals("data")){
				ArrayList<LinkedHashMap<Object, Object>> nesteList = (ArrayList<LinkedHashMap<Object, Object>>) numbers.get(i);
				for(LinkedHashMap<Object, Object> j :nesteList ){
					for(Object nestedKeys: j.keySet()){
						if(nestedKeys.toString().equals("options")){
							//This contains the greeks
							LinkedHashMap<Object, Object> k= (LinkedHashMap<Object, Object>) j.get(nestedKeys);
							for(Object nestedKeys2: k.keySet()){
								ArrayList<LinkedHashMap<Object, Object>> greeks = (ArrayList<LinkedHashMap<Object, Object>>) k.get(nestedKeys2);
								for(LinkedHashMap<Object, Object> finalLinkedHashMap: greeks){
									for(Object p : finalLinkedHashMap.keySet()){

										System.out.println("--|Key: "+p.toString()+", Key type: "+p.getClass()+"| Value: "+finalLinkedHashMap.get(p)+", Value type: "+finalLinkedHashMap.get(p).getClass());

									}
								}

							}


						}else{
							System.out.println("-| Key: "+nestedKeys.toString()+", Key Type: "+nestedKeys.getClass()+"| Value: "+j.get(nestedKeys)+", Value type: "+j.get(nestedKeys).getClass());
						}

					}
				}
			}else{
				System.out.println("Key: "+i.toString()+", Key Type: "+i.getClass()+"| value: "+numbers.get(i)+", value type: "+numbers.get(i).getClass());
			}

		}
		System.out.println("---------------------------------------------------");
		//STOP TEST


		// TEST: WE NEED TO MAKE SURE IF WE CAN GET THE ENTIRE OPTION CHAIN = SUCCESFUL

		//key: 63af4b148fbb43.82883652
		String reqUrl = "https://eodhistoricaldata.com/api/options/AAPL.US?api_token=demo&from=2022-12-30&to=2022-12-30";
		RestTemplate restTemplate3 = new RestTemplate();
		String fooResourceUrl3
				= reqUrl;
		ResponseEntity<Object> response6
				= restTemplate3.getForEntity(fooResourceUrl3, Object.class);
		Object  body2 = response6.getBody();
		//LinkedHashMap<Object, Object> numbers2 = (LinkedHashMap<Object, Object>) body;

		//the body2.toString() delivers the right response: the WHOLE CHAIN
		//System.out.println("164. response: "+body2.toString()+"| type: "+body2.getClass());

		// ---------------------- Call 1 Tabel setup

		LinkedHashMap<Object, Object> LinkedHMap1 = (LinkedHashMap<Object, Object>) body2;

		System.out.println("170. body type: "+body2.getClass());
		System.out.println("----------- START: Get Direct Access Test ----------");
		ArrayList<Object> arrListT = (ArrayList<Object>) LinkedHMap1.get("data");
		LinkedHashMap<Object, Object> LinkedHMap2T = (LinkedHashMap<Object, Object>)arrListT.get(0);
		String expDate = (String) LinkedHMap2T.get("expirationDate");
		System.out.println("Get expiration date: "+expDate);

		//now get directly into the individual contracts
		LinkedHashMap<Object, Object> LinkedHMap3T = (LinkedHashMap<Object, Object>)LinkedHMap2T.get("options");
		ArrayList<Object> arrList2T = (ArrayList<Object>) LinkedHMap3T.get("CALL");

		Object bid;
		Object ask;
		Object ContractName;
		Object Delta;
		Object Strike;
		Object Gamma;
		Object Theta;
		Object Vega;

		DecimalFormat decfor = new DecimalFormat("0.00");

		for(Object i2: arrList2T){
			LinkedHashMap<Object, Object> LinkedHMap4T = (LinkedHashMap<Object, Object>)i2;
			System.out.println("---- Individual Call ----");
			Call1 temp = new Call1();
			bid = LinkedHMap4T.get("bid");
			if(bid.getClass().equals(java.lang.Double.class)){
				//System.out.println(bid.getClass());
				Double tempDoubl = (Double) bid;

				float tempBid = tempDoubl.floatValue();
				float tempBid2 = Float.parseFloat(decfor.format(tempBid));
				//System.out.println("deciman 2nd place: "+tempBid2);
				temp.setBid(tempBid);

			}else if(bid.getClass().equals(java.lang.Integer.class)){
				Integer tempInt = (Integer) bid;

				float tempBid = tempInt.floatValue();
				float tempBid2 = Float.parseFloat(decfor.format(tempBid));
				temp.setBid(tempBid);
				//System.out.println(bid.getClass());
			}else{
				System.out.println("ISSUE WITH Class"+LinkedHMap4T.get("bid").getClass());
			}
			System.out.println("Get Bid: "+temp.getBid()); // done

			ask = LinkedHMap4T.get("ask");
			if(ask.getClass().equals(java.lang.Double.class)){
				//System.out.println(bid.getClass());
				Double tempDoubl = (Double) ask;

				float tempBid = tempDoubl.floatValue();
				float tempBid2 = Float.parseFloat(decfor.format(tempBid));
				temp.setAsk(tempBid);

			}else if(ask.getClass().equals(java.lang.Integer.class)){
				Integer tempInt = (Integer) ask;

				float tempBid = tempInt.floatValue();
				float tempBid2 = Float.parseFloat(decfor.format(tempBid));
				temp.setAsk(tempBid);
				//System.out.println(bid.getClass());
			}else{
				System.out.println("ISSUE WITH Class"+LinkedHMap4T.get("ask").getClass());
			}
			System.out.println("Get Ask: "+temp.getAsk()); // done

			ContractName = LinkedHMap4T.get("contractName");
			temp.setContractName((String) ContractName);
			System.out.println("Get ContractName: "+temp.getContractName()); // done

			Delta = LinkedHMap4T.get("delta");
			if(Delta.getClass().equals(java.lang.Double.class)){
				//System.out.println(bid.getClass());
				Double tempDoubl = (Double) Delta;

				float tempBid = tempDoubl.floatValue();
				float tempBid2 = Float.parseFloat(decfor.format(tempBid));
				temp.setDelta(tempBid);

			}else if(Delta.getClass().equals(java.lang.Integer.class)){
				Integer tempInt = (Integer) Delta;

				float tempBid = tempInt.floatValue();
				float tempBid2 = Float.parseFloat(decfor.format(tempBid));
				temp.setDelta(tempBid);
				//System.out.println(bid.getClass());
			}else{
				System.out.println("ISSUE WITH Class"+LinkedHMap4T.get("ask").getClass());
			}
			System.out.println("Get Delta: "+temp.getDelta()); // done

			Strike = LinkedHMap4T.get("strike");
			if(Strike.getClass().equals(java.lang.Double.class)){
				//System.out.println(bid.getClass());
				Double tempDoubl = (Double) Strike;

				float tempBid = tempDoubl.floatValue();
				float tempBid2 = Float.parseFloat(decfor.format(tempBid));
				temp.setStrike(tempBid);

			}else if(Strike.getClass().equals(java.lang.Integer.class)){
				Integer tempInt = (Integer) Strike;

				float tempBid = tempInt.floatValue();
				float tempBid2 = Float.parseFloat(decfor.format(tempBid));
				temp.setStrike(tempBid);
				//System.out.println(bid.getClass());
			}else{
				System.out.println("ISSUE WITH Class"+LinkedHMap4T.get("ask").getClass());
			}
			System.out.println("Get Strike: "+temp.getStrike()); // done


			Gamma = LinkedHMap4T.get("gamma");
			if(Gamma.getClass().equals(java.lang.Double.class)){
				//System.out.println(bid.getClass());
				Double tempDoubl = (Double) Gamma;

				float tempBid = tempDoubl.floatValue();
				float tempBid2 = Float.parseFloat(decfor.format(tempBid));
				temp.setGamma(tempBid);

			}else if(Gamma.getClass().equals(java.lang.Integer.class)){
				Integer tempInt = (Integer) Gamma;

				float tempBid = tempInt.floatValue();
				float tempBid2 = Float.parseFloat(decfor.format(tempBid));
				temp.setGamma(tempBid);
				//System.out.println(bid.getClass());
			}else{
				System.out.println("ISSUE WITH Class");
			}
			System.out.println("Get Gamma: "+temp.getGamma());

			Theta = LinkedHMap4T.get("theta");
			if(Theta.getClass().equals(java.lang.Double.class)){
				//System.out.println(bid.getClass());
				Double tempDoubl = (Double) Theta;

				float tempBid = tempDoubl.floatValue();
				float tempBid2 = Float.parseFloat(decfor.format(tempBid));
				temp.setTheta(tempBid);

			}else if(Theta.getClass().equals(java.lang.Integer.class)){
				Integer tempInt = (Integer) Theta;

				float tempBid = tempInt.floatValue();
				float tempBid2 = Float.parseFloat(decfor.format(tempBid));
				temp.setTheta(tempBid);
				//System.out.println(bid.getClass());
			}else{
				System.out.println("ISSUE WITH Class");
			}
 			System.out.println("Get Theta: "+temp.getTheta());

			Vega = LinkedHMap4T.get("vega");
			if(Vega.getClass().equals(java.lang.Double.class)){
				//System.out.println(bid.getClass());
				Double tempDoubl = (Double) Vega;

				float tempBid = tempDoubl.floatValue();
				float tempBid2 = Float.parseFloat(decfor.format(tempBid));
				temp.setVega(tempBid);

			}else if(Vega.getClass().equals(java.lang.Integer.class)){
				Integer tempInt = (Integer) Vega;

				float tempBid = tempInt.floatValue();
				float tempBid2 = Float.parseFloat(decfor.format(tempBid));
				temp.setVega(tempBid);
				//System.out.println(bid.getClass());
			}else{
				System.out.println("ISSUE WITH Class");
			}
			System.out.println("Get Vega: "+temp.getVega());

			//set the buffer for buyer
			float bufferForBuyer = (float) (temp.getAsk()/temp.getStrike());
			float bufferForBuyer2 = Float.parseFloat(decfor.format(bufferForBuyer));
			//System.out.println("Get bufferForBuyer: "+bufferForBuyer+"| "+(long) bufferForBuyer);
			temp.setBufferForBuyer( bufferForBuyer);

			//set the buffer to seller
			float bufferForSeller = (float) (temp.getBid()/temp.getStrike());
			float bufferForSeller2 = Float.parseFloat(decfor.format(bufferForBuyer));
			temp.setBufferForSeller(bufferForSeller);
			temp.setExp(expDate);

			service.saveOrUpdate(temp);
		}

		// CALL TABLE HAS BEEN CHECKED AND VERIFIED






		/*
		System.out.println("----------- Showing Puts ----------");
		ArrayList<Object> arrList2TP = (ArrayList<Object>) LinkedHMap3T.get("PUT");
		for(Object i2: arrList2TP){
			LinkedHashMap<Object, Object> LinkedHMap4T = (LinkedHashMap<Object, Object>)i2;
			System.out.println("---- Individual Put ----");
			System.out.println("Get Bid: "+LinkedHMap4T.get("bid"));
			System.out.println("Get Ask: "+LinkedHMap4T.get("ask"));
			System.out.println("Get ContractName: "+LinkedHMap4T.get("contractName"));
			System.out.println("Get Delta: "+LinkedHMap4T.get("delta"));
			System.out.println("Get Strike: "+LinkedHMap4T.get("strike"));
			System.out.println("Get Gamma: "+LinkedHMap4T.get("gamma"));
			System.out.println("Get Theta: "+LinkedHMap4T.get("theta"));
			System.out.println("Get Vega: "+LinkedHMap4T.get("vega"));

		}

		 */



		System.out.println("-----------STOP: Get Direct Access Test ----------");

		for(Object key: LinkedHMap1.keySet()){
			if(key.toString().equals("data")){
				System.out.println("--Now in DATA-- key: data");
				ArrayList<Object> arrList = (ArrayList<Object>) LinkedHMap1.get(key);

				for(Object i: arrList){

					System.out.println("179. type: "+i.getClass());
					LinkedHashMap<Object, Object> LinkedHMap2 = (LinkedHashMap<Object, Object>)i;
					for(Object key2: LinkedHMap2.keySet()){
						if(key2.toString().equals("options")){
							System.out.println("--Now in DATA: THE CHAIN-- key: options");
							LinkedHashMap<Object, Object> LinkedHMap3 = (LinkedHashMap<Object, Object>)LinkedHMap2.get(key2);
							for(Object key3: LinkedHMap3.keySet()){
								if(key3.toString().equals("CALL") ||key3.toString().equals("PUT") ){

									ArrayList<Object> arrList2 = (ArrayList<Object>) LinkedHMap3.get(key3);
									for(Object i2: arrList2){
										System.out.println("---Now in DATA: THE CHAIN: IN INDIVIDUAL CONTRACT----key: "+key3.toString()+"----------------------------------------------");
										LinkedHashMap<Object, Object> LinkedHMap4 = (LinkedHashMap<Object, Object>)i2;
										for(Object i3: LinkedHMap4.keySet()){
											System.out.print("----Key: "+i3.toString());
											System.out.print(", Key type: "+i3.getClass());
											System.out.println("| Value: "+LinkedHMap4.get(i3));
											//System.out.println(", Value type: "+LinkedHMap4.get(i3).getClass());
												//System.out.println("----Key: "+i3.toString()+", Key type: "+i3.getClass()+"| Value: "+LinkedHMap4.get(i3)+", Value type: "+LinkedHMap4.get(i3).getClass());


										}

									}
								}else{
									System.out.println("---Key: "+key3.toString()+", key type: "+key3.getClass()+" | Value: "+LinkedHMap3.get(key3)+", Value type: "+LinkedHMap3.get(key3).getClass());
								}

							}
						}else{
							System.out.println("-Key: "+key2+", Key Type: "+key2.getClass()+" | Value: "+LinkedHMap2.get(key2)+", Value type: "+LinkedHMap2.get(key2).getClass());
						}

					}
				}
			}else{
				System.out.println("Key: "+key+", Key Type: "+key.getClass()+" | Value: "+LinkedHMap1.get(key)+", Value type: "+LinkedHMap1.get(key).getClass());
			}

		}


		//

		// TEST: HOW TO PERIODICALLY SEND OR GET DATA: SUCCESSFUL
		LocalDateTime then = LocalDateTime.now();
		int count =0;
		while (true) {
			// logic
			// we need to update the databases every X time period
			if (ChronoUnit.SECONDS.between(then, LocalDateTime.now()) >= 5) {
				System.out.println("5 seconds passed");
				then = LocalDateTime.now();
				count++;
				if(count>=10){
					break;
				}
			}
		}

		//TESTING DELETE: PASS
		//service.deleteAll();


		// START OF TEST: POPULATE PUT1 TABLE: PASS
		ArrayList<Object> arrList2P = (ArrayList<Object>) LinkedHMap3T.get("PUT");

		Object bidP;
		Object askP;
		Object ContractNameP;
		Object DeltaP;
		Object StrikeP;
		Object GammaP;
		Object ThetaP;
		Object VegaP;

		System.out.println("----------- Showing Puts ----------");

		for(Object i2: arrList2P){
			LinkedHashMap<Object, Object> LinkedHMap4T = (LinkedHashMap<Object, Object>)i2;
			System.out.println("---- Individual Call ----");
			Put1 temp = new Put1();
			bid = LinkedHMap4T.get("bid");
			if(bid.getClass().equals(java.lang.Double.class)){
				//System.out.println(bid.getClass());
				Double tempDoubl = (Double) bid;

				float tempBid = tempDoubl.floatValue();
				float tempBid2 = Float.parseFloat(decfor.format(tempBid));
				temp.setBid(tempBid);

			}else if(bid.getClass().equals(java.lang.Integer.class)){
				Integer tempInt = (Integer) bid;

				float tempBid = tempInt.floatValue();
				float tempBid2 = Float.parseFloat(decfor.format(tempBid));
				temp.setBid(tempBid);
				//System.out.println(bid.getClass());
			}else{
				System.out.println("ISSUE WITH Class");
			}
			System.out.println("Get Bid: "+temp.getBid()); // done

			ask = LinkedHMap4T.get("ask");
			if(ask.getClass().equals(java.lang.Double.class)){
				//System.out.println(bid.getClass());
				Double tempDoubl = (Double) ask;

				float tempBid = tempDoubl.floatValue();
				float tempBid2 = Float.parseFloat(decfor.format(tempBid));
				temp.setAsk(tempBid);

			}else if(ask.getClass().equals(java.lang.Integer.class)){
				Integer tempInt = (Integer) ask;

				float tempBid = tempInt.floatValue();
				float tempBid2 = Float.parseFloat(decfor.format(tempBid));
				temp.setAsk(tempBid);
				//System.out.println(bid.getClass());
			}else{
				System.out.println("ISSUE WITH Class");
			}
			System.out.println("Get Ask: "+temp.getAsk()); // done

			ContractName = LinkedHMap4T.get("contractName");
			temp.setContractName((String) ContractName);
			System.out.println("Get ContractName: "+temp.getContractName()); // done

			Delta = LinkedHMap4T.get("delta");
			if(Delta.getClass().equals(java.lang.Double.class)){
				//System.out.println(bid.getClass());
				Double tempDoubl = (Double) Delta;

				float tempBid = tempDoubl.floatValue();
				float tempBid2 = Float.parseFloat(decfor.format(tempBid));
				temp.setDelta(tempBid);

			}else if(Delta.getClass().equals(java.lang.Integer.class)){
				Integer tempInt = (Integer) Delta;

				float tempBid = tempInt.floatValue();
				float tempBid2 = Float.parseFloat(decfor.format(tempBid));
				temp.setDelta(tempBid);
				//System.out.println(bid.getClass());
			}else{
				System.out.println("ISSUE WITH Class");
			}
			System.out.println("Get Delta: "+temp.getDelta()); // done

			Strike = LinkedHMap4T.get("strike");
			if(Strike.getClass().equals(java.lang.Double.class)){
				//System.out.println(bid.getClass());
				Double tempDoubl = (Double) Strike;

				float tempBid = tempDoubl.floatValue();
				float tempBid2 = Float.parseFloat(decfor.format(tempBid));
				temp.setStrike(tempBid);

			}else if(Strike.getClass().equals(java.lang.Integer.class)){
				Integer tempInt = (Integer) Strike;

				float tempBid = tempInt.floatValue();
				float tempBid2 = Float.parseFloat(decfor.format(tempBid));
				temp.setStrike(tempBid);
				//System.out.println(bid.getClass());
			}else{
				System.out.println("ISSUE WITH Class");
			}
			System.out.println("Get Strike: "+temp.getStrike()); // done


			Gamma = LinkedHMap4T.get("gamma");
			if(Gamma.getClass().equals(java.lang.Double.class)){
				//System.out.println(bid.getClass());
				Double tempDoubl = (Double) Gamma;

				float tempBid = tempDoubl.floatValue();
				float tempBid2 = Float.parseFloat(decfor.format(tempBid));
				temp.setGamma(tempBid);

			}else if(Gamma.getClass().equals(java.lang.Integer.class)){
				Integer tempInt = (Integer) Gamma;

				float tempBid = tempInt.floatValue();
				float tempBid2 = Float.parseFloat(decfor.format(tempBid));
				temp.setGamma(tempBid);
				//System.out.println(bid.getClass());
			}else{
				System.out.println("ISSUE WITH Class");
			}
			System.out.println("Get Gamma: "+temp.getGamma());

			Theta = LinkedHMap4T.get("theta");
			if(Theta.getClass().equals(java.lang.Double.class)){
				//System.out.println(bid.getClass());
				Double tempDoubl = (Double) Theta;

				float tempBid = tempDoubl.floatValue();
				float tempBid2 = Float.parseFloat(decfor.format(tempBid));
				temp.setTheta(tempBid);

			}else if(Theta.getClass().equals(java.lang.Integer.class)){
				Integer tempInt = (Integer) Theta;

				float tempBid = tempInt.floatValue();
				float tempBid2 = Float.parseFloat(decfor.format(tempBid));
				temp.setTheta(tempBid);
				//System.out.println(bid.getClass());
			}else{
				System.out.println("ISSUE WITH Class");
			}
			System.out.println("Get Theta: "+temp.getTheta());

			Vega = LinkedHMap4T.get("vega");
			if(Vega.getClass().equals(java.lang.Double.class)){
				//System.out.println(bid.getClass());
				Double tempDoubl = (Double) Vega;

				float tempBid = tempDoubl.floatValue();
				float tempBid2 = Float.parseFloat(decfor.format(tempBid));
				temp.setVega(tempBid);

			}else if(Vega.getClass().equals(java.lang.Integer.class)){
				Integer tempInt = (Integer) Vega;

				float tempBid = tempInt.floatValue();
				float tempBid2 = Float.parseFloat(decfor.format(tempBid));
				temp.setVega(tempBid);
				//System.out.println(bid.getClass());
			}else{
				System.out.println("ISSUE WITH Class");
			}
			System.out.println("Get Vega: "+temp.getVega());

			//set the buffer for buyer
			float bufferForBuyer = (float) (temp.getAsk()/temp.getStrike());
			float bufferForBuyer2 = Float.parseFloat(decfor.format(bufferForBuyer));
			//System.out.println("Get bufferForBuyer: "+bufferForBuyer+"| "+(long) bufferForBuyer);
			temp.setBufferForBuyer( bufferForBuyer);

			//set the buffer to seller
			float bufferForSeller = (float) (temp.getBid()/temp.getStrike());
			float bufferForSeller2 = Float.parseFloat(decfor.format(bufferForSeller));
			temp.setBufferForSeller(bufferForSeller);
			temp.setExp(expDate);

			servicePut.saveOrUpdate(temp);
		}

		// TEST: GET AMD OPTIONS: NO ACCESS

		// need to pay to get access

		// START: TEST: GET OPTIONS WITHIN A CERTAIN DELTA: Custom SQL query: SUCCESS
			List<Call1> returnList = service.customQuery();
			for(Call1 ob : returnList){
				System.out.println("--"+ob.getStrike()+"--");
			}

		// STOP: TEST: GET OPTIONS WITHIN A CERTAIN DELTA: Custom SQL query

		// https://dzone.com/articles/add-custom-functionality-to-a-spring-data-reposito
		//https://www.baeldung.com/spring-data-entitymanager






		/*

		String reqUrl2 = "https://eodhistoricaldata.com/api/options/AMD.US?api_token=demo&from=2022-12-30&to=2022-12-30";
		RestTemplate restTemplate4 = new RestTemplate();
		String fooResourceUrl4
				= reqUrl2;
		ResponseEntity<Object> response7
				= restTemplate4.getForEntity(fooResourceUrl4, Object.class);
		Object  body3 = response7.getBody();

		LinkedHashMap<Object, Object> LinkedHMapAMD = (LinkedHashMap<Object, Object>) body3;
		System.out.println("----------- START: Get Direct Access Test ----------");
		ArrayList<Object> arrListAMD = (ArrayList<Object>) LinkedHMapAMD.get("data");
		LinkedHashMap<Object, Object> LinkedHMapAMD2 = (LinkedHashMap<Object, Object>)arrListAMD.get(0);
		String expDateAMD = (String) LinkedHMapAMD2.get("expirationDate");
		System.out.println("718. Get expiration date: "+expDate);

		LinkedHashMap<Object, Object> LinkedHMapAMD3 = (LinkedHashMap<Object, Object>)LinkedHMapAMD2.get("options");
		ArrayList<Object> arrListAMD2 = (ArrayList<Object>) LinkedHMapAMD3.get("CALL");

		Object bidAMD;
		Object askAMD;
		Object ContractNameAMD;
		Object DeltaAMD;
		Object StrikeAMD;
		Object GammaAMD;
		Object ThetaAMD;
		Object VegaAMD;

		for(Object i2: arrListAMD2) {
			LinkedHashMap<Object, Object> LinkedHMapAMD4 = (LinkedHashMap<Object, Object>) i2;
			System.out.println("---- Individual Call AMD ----");
			Put1 temp = new Put1();
			bidAMD = LinkedHMapAMD4.get("bid");
			askAMD = LinkedHMapAMD4.get("ask");
			ContractNameAMD = LinkedHMapAMD4.get("contractName");
			StrikeAMD = LinkedHMapAMD4.get("strike");
			DeltaAMD = LinkedHMapAMD4.get("delta");
			GammaAMD = LinkedHMapAMD4.get("gamma");
			ThetaAMD = LinkedHMapAMD4.get("theta");
			VegaAMD = LinkedHMapAMD4.get("vega");

			System.out.println("Bid: "+bidAMD);
			System.out.println("Ask: "+askAMD);
			System.out.println("ContractName: "+ContractNameAMD);
			System.out.println("Strike: "+StrikeAMD);
			//System.out.println("Bid: "+bidAMD);
		}

		 */

		// END OF TEST: POPULATE PUT1 TABLE: TEST PASS



		// START: Create PUT table for AMD



		// STOP: Create PUT table for AMD

		//STOP

		/*
		* Now we need to fine tune the above operation to extract exactly what we need for the database
		*
		* the database design has to be defined before we complete above step
		*
		* We will have 1 database per stock ticker
		* 	inside the database ticker we will have 2 * number of expiration dates up to 45 days
		* 	the naming convention will be ex: CDATE or PDATE
		* 		Inside this table, ex: CDATE
		* 			contractName: String: ex: AAPL221230P00170000
		* 			strike: String: ex: 170
		* 			bid: String: ex: 38
		* 			ask: String: ex: 42.3
		* 			delta: String: ex: -0.8593 (we will drop the -/+)
		* 			gamma: String: ex: 0.0076
		* 			theta: String: ex: -3.2827 (we will drop the -/+)
		* 			vega: String: ex: 0.0151
		* 			buffer for buyer:  String: ask/strike, this should be a decimal value ex: 0.1
		* 			buffer for seller: String: bid/strike, this should be a decimal value ex: 0.1
		*
		* */

		/*
		* Step 1: Make a database with above specs
		*
		* */

		/*
		*
		* How to get into postgresql:
		* 	psql postgres -U postgres
		*
		* How to create a database:
		* 	CREATE DATABASE databasename;
		*
		* How to give all access to user postgres
		* 	GRANT ALL PRIVILEGES ON DATABASE databasename TO postgres; postgres=> \list
		* 	GRANT ALL PRIVILEGES ON DATABASE aapl TO postgres; postgres=> \list
		*
		* How to view all databases:
		* 	\l
		*
		* How to enter database:
		* 	\d databaseName
		*
		* How to view tables once inside database:
		*	\d
		*
		* How to connect to database
		* 	\connect databasename
		*
		* How to go into table
		* 	\d tableName
		*
		* How to see all contents in table
		* 	SELECT * FROM mytablename;
		* OR
		* 	SELECT * FROM mytablename LIMIT 10;
		*
		 * */

		/*
		* Current tables are in database1, you have to connect to it (\connect databasename) then show tables using: \d
		* Debug server errors:
		* Process port
		*
		* How to check what process is running on port
		* lsof -i :portNumber
		*
		* How to kill process:
		* kill -9 PID
		* PID is the code u se when u do lsof -i
		*
		*
		* */

		// https://www.yawintutor.com/how-to-connect-multiple-databases-using-spring-boot/

		// https://www.youtube.com/watch?v=nzszxQbQ5WU&ab_channel=NareshiTechnologies







	}



}
