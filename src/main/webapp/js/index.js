//A function to display time
function displayTime() {
	var date = new Date();
	document.getElementById("time").innerHTML = date;
	//will display time again, every 1001 miliseconds.
	timer = setTimeout("displayTime()", 1001)
}

//A function to get all data from hanzi_data table by requesting to the server
function getAllHanzi() {
	//make request to Hanziserver using ajax.
	//ajax programming use XMLHttpRequest().
	var ajax = new XMLHttpRequest();

	//must use this block code, to read the http response body:
	ajax.onreadystatechange = function() {
		//When readyState property is 4 and the status property is 200, the response is ready:
		if (this.readyState == 4 && this.status == 200) {
			
			//get the response from server.
			var responseString = ajax.responseText;

			//if the response is null, or "Not Found.", then print error message and exit.
			if (responseString == null || responseString === "Not found.") {
				document.getElementById("data").innerHTML = "Not found.";
				return;
			}

			//if the response is error, then print error message and exit.
			if (responseString === "Error when getting all hanzi data.") {
				document.getElementById("data").innerHTML = "Error when getting all hanzi data.";
				return;
			}

			//convert from string into javasript object:
			var responseObject = JSON.parse(responseString);

			//get the "hanzi" and "created_date" from json .
			var result = "";
			for (i in responseObject.hanzi_data) {
				var hanzi = responseObject.hanzi_data[i].hanzi;
				var created_date_epoch_string = responseObject.hanzi_data[i].created_date;

				//convert the created_date_epoch into javascript date.
				var created_date_epoch_int = parseInt(created_date_epoch_string);
				var created_date = new Date(created_date_epoch_int);

				result = result + hanzi + "， " + created_date + "<br/>";
				document.getElementById("data").innerHTML = result;
			}

		}
	}

	//setting the http request.
	ajax.open("GET", "http://localhost:9097/learn-hanzi/api/hanzi/getAllHanzi",
			true);

	//send the http request.
	ajax.send();

}

//A function to search the unique hanzi from the inputted text box, by requesting to the server.
function searchHanzi() {

	//clear error message, if there are error message.
	document.getElementById("result").innerHTML = "";

	var search = document.getElementById("search").value;

	if (search == null) {
		document.getElementById("result").innerHTML = "Please input Hanzi";
		return;
	}

	if (isEmpty(search)) {
		document.getElementById("result").innerHTML = "Please insert Hanzi";
		return;
	}

	//make request to Hanziserver using ajax.
	//ajax programming use XMLHttpRequest().
	var ajax = new XMLHttpRequest();

	//must use this block code, to read the http response body:
	ajax.onreadystatechange = function() {
		//When readyState property is 4 and the status property is 200, the response is ready:
		if (this.readyState == 4 && this.status == 200) {
			
			//get the response from server.
			var responseString = ajax.responseText;

			if (responseString === "The request body cannot be read.") {
				document.getElementById("result").innerHTML = "Error: Server cannot procees the request.";
				return;
			}

			if (responseString === "The request body cannot be empty.") {
				document.getElementById("result").innerHTML = "Error: Server cannot procees the request.";
				return;
			}

			if (responseString === "Not found.") {
				document.getElementById("result").innerHTML = "Hanzi is not found !";
				return;
			}

			if (responseString === "Error when searching hanzi data.") {
				document.getElementById("result").innerHTML = "Error: Server cannot procees the request.";
				return;
			}

			//convert from string into javasript object:
			var responseObject = JSON.parse(responseString);

			//get the "hanzi" and "created_date" from json .
			var result = "";
			for (i in responseObject.hanzi_data) {

				var hanzi = responseObject.hanzi_data[i].hanzi;
				var created_date_epoch_string = responseObject.hanzi_data[i].created_date;
				var created_date_epoch_int = parseInt(created_date_epoch_string);
				var created_date = new Date(created_date_epoch_int);
				result = result + hanzi + "， " + created_date + "<br/>";
				document.getElementById("result").innerHTML = result;
			}

		}
	}

	//setting the http request.
	ajax.open("POST",
			"http://localhost:9097/learn-hanzi/api/hanzi/searchHanzi", true);

	//You must call setRequestHeader()after open(), but before send().
	ajax.setRequestHeader("Content-type", "text/plain;charset=UTF-8");

	//send the http request to server, with request body from input text box.
	var inputBox = document.getElementById("search").value;
	ajax.send(inputBox);

}

//A function to add the unique hanzi from the inputted text box to database, by requesting to the server.
function addHanzi() {

	//clear error message, if there are error message.
	document.getElementById("result").innerHTML = "";

	var input = document.getElementById("input").value;

	if (input == null) {
		document.getElementById("result").innerHTML = "Please insert Hanzi.";
		return;
	}

	if (isEmpty(input)) {
		document.getElementById("result").innerHTML = "Please insert Hanzi.";
		return;
	}

	//make request to server using ajax.
	//ajax programming use XMLHttpRequest()
	var ajax = new XMLHttpRequest();

	//must use this block code, to read the http response body:
	ajax.onreadystatechange = function() {
		//When readyState property is 4 and the status property is 200, the response is ready:
		if (this.readyState == 4 && this.status == 200) {
			
			//get the response from server.
			var responseString = ajax.responseText;

			if (responseString === "The request body cannot be read.") {
				document.getElementById("result").innerHTML = "Error: Server cannot procees the request.";
				return;
			}
			if (responseString === "The request body cannot be empty.") {
				document.getElementById("result").innerHTML = "Error: Server cannot procees the request.";
				return;
			}
			if (responseString === "Error: Cannot insert. Data already exist.") {
				document.getElementById("result").innerHTML = "Error: Cannot insert. Data already exist.";
				return;
			}
			if (responseString === "Error when inserting hanzi data.") {
				document.getElementById("result").innerHTML = "Error when inserting hanzi data";
				return;
			}

			//convert from string into javasript object:
			var responseObject = JSON.parse(responseString);

			//get the "hanzi" and "created_date" from json .
			var result = "";
			for (i in responseObject.hanzi_data) {
				var hanzi = responseObject.hanzi_data[i].hanzi;
				var created_date_epoch_string = responseObject.hanzi_data[i].created_date;
				var created_date_epoch_int = parseInt(created_date_epoch_string);
				var created_date = new Date(created_date_epoch_int);
				result = result + hanzi + "， " + created_date + " Added !"
						+ "<br/>";
				document.getElementById("result").innerHTML = result;
			}

		}
	}

	//setting the http request.
	ajax.open("POST",
			"http://localhost:9097/learn-hanzi/api/hanzi/insertHanzi", true);

	//You must call setRequestHeader()after open(), but before send().
	ajax.setRequestHeader("Content-type", "text/plain;charset=UTF-8");

	//send the http request to server, with request body from input text box.
	ajax.send(input);
}

//A function to validate the String.
function isEmpty(input) {

	//remove all whitespace from input.
	removedWhiteSpaceInput = input.replace(/\s+/g, "");

	//if input contains no character left after the whitespace removed, it is then an empty string.
	if (removedWhiteSpaceInput.length === 0) {
		return true;
	} else {
		return false;
	}
}