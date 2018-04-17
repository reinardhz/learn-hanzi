//A function to display time
function displayTime() {
	var date = new Date();
	document.getElementById("time").innerHTML = date;
	//will display time again, every 1001 miliseconds
	timer = setTimeout("displayTime()", 1001)
}

//A function to add a new unique book name from the inputted text box to database, by requesting to the server.
//this function is tested OK
function insertBookName(){
	
	//clear error message, if there are error message.
	document.getElementById("resultAddBook").innerHTML = "";
	
	var input = document.getElementById("inputBook").value;
	
	if(input==null){
		document.getElementById("resultAddBook").innerHTML = "Please insert Book Name.";
		return;
	}
	
	if(isEmpty(input)){
		document.getElementById("resultAddBook").innerHTML = "Please insert Book Name.";
		return;
	}
	
	//make request to server using ajax.
	//ajax programming use XMLHttpRequest()
	var ajax = new XMLHttpRequest();
	
	//must use this block code, to read the http response body:
	ajax.onreadystatechange = function() {
		//When readyState property is 4 and the status property is 200, the response is ready:
		if (this.readyState == 4 && this.status == 200) {
			
			var responseString = ajax.responseText;
			
			if (responseString === "The request body cannot be read.") {
				document.getElementById("resultAddBook").innerHTML = "Error: Server cannot procees the request.";
				return;
			}
			if (responseString === "The request body cannot be empty.") {
				document.getElementById("resultAddBook").innerHTML = "Error: Server cannot procees the request.";
				return;
			}
			if (responseString === "Error: Cannot insert. Data already exist.") {
				document.getElementById("resultAddBook").innerHTML = "Error: Cannot insert. Data already exist.";
				return;
			}
			if (responseString === "Error when inserting book name.") {
				document.getElementById("resultAddBook").innerHTML = "Error when inserting book name";
				return;
			}
			
			//display the result
			document.getElementById("resultAddBook").innerHTML = responseString;
			
		}
		
	}
	
	//setting the http request.
	ajax.open("POST", "http://localhost:9097/learn-hanzi/api/book/insertBookName", true);
	
	//You must call setRequestHeader()after open(), but before send().
	ajax.setRequestHeader("Content-type", "text/plain;charset=UTF-8");
	
	//send the http request to server, with request body from input text box.
	ajax.send(input);
}



//A function to get all book name from the database, by requesting to the server.
//then, display all book name in the drop-down list.
function getAllBookName(){
	//make request to Hanziserver using ajax.
	//ajax programming use XMLHttpRequest().
	var ajax = new XMLHttpRequest();
	
	//must use this block code, to read the http response body:
	ajax.onreadystatechange = function() {
		//When readyState property is 4 and the status property is 200, the response is ready:
		if (this.readyState == 4 && this.status == 200) {
			
			//get the response from server
			var responseString = ajax.responseText;
			
			//if response not found then exit this function, do not proceed.
			if(responseString === "Not found."){
				return;
			}
			//if there is error then exit this function, do not proceed.
			if(responseString === "Error when getting all book name."){
				return;
			}
			
			//splits the responseString into an array of strings using the comma separator
			var dataBookArray = responseString.split(",");
			
			//get the <select> element DOM object.
			var bookList = document.getElementById("bookList");
			
			//===clear the <option> tag from <select> element DOM object===
			//1. Get the number of how many <option> element this object has.
		    var size = bookList.length;
		    
		    //2. Lopping as many number of the <option> element, inside bookList.
		    for(var i=0; i<size; ++i){
		    	//remove the <option> element, in index 0, to ensure all tag is removed.
		    	bookList.remove(0);
			}
			//================================
			
			//===Display the dropdown list from array===
		    
		    //===set the initial content===
			//create new <option> tag.
		    var newOptionTag = document.createElement("option");
		        
		    //set the content of the created <option> tag.
		    newOptionTag.textContent = '---Choose Book---';
		    
		    //set the attribut "value" in created <option> tag.
		    newOptionTag.value = 'initial';
		    
		    //add the created <option> tag inside <select> element.
		    bookList.add(newOptionTag);
		    //===========================
		    
			for(var i = 0; i < dataBookArray.length; ++i) {
				var bookName = dataBookArray[i];
				
				//create new <option> tag.
		    	var newOptionTag = document.createElement("option");
		    	
		    	//set the content of the created <option> tag.
		    	newOptionTag.textContent = bookName;
		    	
		    	//set the attribut "value" in created <option> tag.
		    	newOptionTag.value = bookName;
		    	
		    	//add the created <option> tag inside <select> element.
		    	bookList.add(newOptionTag);
			}
			//================================
			
		}
	}
	
	//setting the http request.
	ajax.open("GET", "http://localhost:9097/learn-hanzi/api/book/getAllBookName",
			true);
	
	//send the http request.
	ajax.send();
}


//A function to get all hanzi stroke on selected book name from drop-down list, by requesting to server.
//This function is executed when user select the option from dropdown list.
function getAllHanziStrokeInBookName(){
	
	//clear the error message, if there is error message.
	document.getElementById("result").innerHTML = "";
	
	//clear the book_name, if there is book name
	document.getElementById("bookName").innerHTML = "";
	
	//clear the dataHanziStroke, if there is.
	document.getElementById("dataHanziStroke").innerHTML = "";
	
	//1. Get the selected book name from drop-down list.
	var selectedBookName = document.getElementById("bookList").value;
	
	//2. display the book name inside tag <span id= "bookName"></span>
	document.getElementById("bookName").innerHTML = selectedBookName;
	
	//===3. Send request to BookController===
	
	//make request to Hanziserver using ajax.
	//ajax programming use XMLHttpRequest().
	var ajax = new XMLHttpRequest();
	
	//must use this block code, to read the http response body:
	ajax.onreadystatechange = function() {
		//When readyState property is 4 and the status property is 200, the response is ready:
		if (this.readyState == 4 && this.status == 200) {
			
			//===Get the response from server and validate it===
			var responseString = ajax.responseText;
			
			if (responseString === "The request body cannot be read.") {
				document.getElementById("dataHanziStroke").innerHTML = "Error: Server cannot procees the request.";
				return;
			}
			if (responseString === "The request body cannot be empty.") {
				document.getElementById("dataHanziStroke").innerHTML = "Error: Server cannot procees the request.";
				return;
			}
			if (responseString === "Not found."){
				document.getElementById("dataHanziStroke").innerHTML = "Not found.";
				return;
			}
			if (responseString === "Error when searching all hanzi stroke in book name."){
				document.getElementById("dataHanziStroke").innerHTML = "Error when searching all hanzi stroke in book name.";
				return;
			}
			//================================
			
			
			//convert from string into javasript object:
			var responseObject = JSON.parse(responseString);
			
			//===Get the "hanzi_stroke" and "created_date" from json, and display it===
			
			//make sure the book_name response is same as book_name selected from drop-down list.
			if(responseObject.book_name !== selectedBookName){
				document.getElementById("result").innerHTML = "Error: The response hanzi stroke is not related with the selected book name.";
				return;
			}
			
			//prepare the result variable.
			var result = "";
			
			//iterate over the object, to get the hanzi_stroke and created_date
			for (i in responseObject.hanzi_stroke_data){
				
				//get the hanzi_stroke from array inside object
				var hanzi_stroke = responseObject.hanzi_stroke_data[i].hanzi_stroke;
				
				//get the page_number from array inside object
				var page_number = responseObject.hanzi_stroke_data[i].page_number;
				
				//get the created_date from array inside object
				var created_date_epoch_string = responseObject.hanzi_stroke_data[i].created_date;
				
				//convert the created_date_epoch into javascript date.
				var created_date_epoch_int = parseInt(created_date_epoch_string);
				var created_date = new Date(created_date_epoch_int);
				
				//prepare the data
				result = result + hanzi_stroke + "， " + page_number + "頁， " + created_date + "<br/>";
				
				//display it inside tag <div id="dataHanziStroke"></div>
				document.getElementById("dataHanziStroke").innerHTML = result;
			}
			//================================
			
			
			//================================
		}
	}
	
	//setting the http request.
	ajax.open("POST", "http://localhost:9097/learn-hanzi/api/book/getAllHanziStrokeInBookName",
			true);
	
	//You must call setRequestHeader()after open(), but before send().
	ajax.setRequestHeader("Content-type", "text/plain;charset=UTF-8");
	
	//send the http request, from the selected drop-down list
	ajax.send(selectedBookName);
	
	//===========================
	
}


//A function to search the hanzi stroke in all book name.
//This function is executed when the "searchHanziStrokeInAllBook" button is clicked.
//input: Hanzi
//output: Hanzi Stroke,book name, page or "Not found."
function searchHanziStrokeInAllBook(){
	
	//clear the error message, if there is error message.
	document.getElementById("result").innerHTML = "";

	//clear the book_name, if there is book name
	document.getElementById("bookName").innerHTML = "";
	
	//clear the dataHanziStroke, if there is.
	document.getElementById("dataHanziStroke").innerHTML = "";
	
	//get the inputted hanzi stroke from input box.
	var input = document.getElementById("searchHanziStrokeInAllBook").value;
	if(input==null){
		document.getElementById("result").innerHTML = "Not found.";
		return;
	}
	if(isEmpty(input)){
		document.getElementById("result").innerHTML = "Not found.";
		return;
	}
	
	//prepare the request body.
	var requestBody = input;
	
	//===Send request to BookController.searchHanziStrokeInAllBook()===
	
	//make request to server using ajax.
	//ajax programming use XMLHttpRequest()
	var ajax = new XMLHttpRequest();
	
	//must use this block code, to read the http response body:
	ajax.onreadystatechange = function() {
		//When readyState property is 4 and the status property is 200, the response is ready:
		if (this.readyState == 4 && this.status == 200) {
			
			//===Get the response from server and parse it===
			//example responseString: {"hanzi_stroke_data_all_book":[{"hanzi_stroke":"生詞","book_name":"第三書","page_number":"六"}, {"hanzi_stroke":"生詞","book_name":"第三書","page_number":"六十三"}]}
			var responseString = ajax.responseText;
			
			if(responseString === 'Not found.'){
				document.getElementById("result").innerHTML = 'Not found.'
				return;
			}
			
			if(responseString === 'The request body must only contain Chinese characters, with no space.'){
				document.getElementById("result").innerHTML = 'The request body must only contain Chinese characters, with no space.';
				return;
			}
			
			if(responseString === 'The request body cannot be empty.'){
				document.getElementById("result").innerHTML = 'The request body cannot be empty.';
				return;
			}
			
			if(responseString === 'The request body cannot be read.'){
				document.getElementById("result").innerHTML = 'The request body cannot be read.';
				return;
			}
			
			if(responseString === 'Error when searching hanzi strokes in all book name.'){
				document.getElementById("result").innerHTML = 'Error when searching hanzi strokes in all book name.';
				return;
			}
			
			
			//convert from string into javasript object:
			var responseObject = JSON.parse(responseString);
			
			//===Get the "hanzi_stroke", "book_name" and "page_number" from json, and display it===
			
			var result = '';
			
			//iterate over the "hanzi_stroke_data_all_book" array
			for (i in responseObject.hanzi_stroke_data_all_book){
				var hanzi_stroke = responseObject.hanzi_stroke_data_all_book[i].hanzi_stroke;
				var book_name = responseObject.hanzi_stroke_data_all_book[i].book_name;
				var page_number = responseObject.hanzi_stroke_data_all_book[i].page_number;
				result = result + 'hanzi_stroke:' + hanzi_stroke + ', book_name:' + book_name + ', page_number:' + page_number + '<br/>';
				
				//display it inside tag <div id="result"></div>
				document.getElementById("result").innerHTML = result;
			}	
		}
	}
	
	//setting the http request.
	ajax.open("POST", "http://localhost:9097/learn-hanzi/api/book/searchHanziStrokeInAllBook", true);
	
	//You must call setRequestHeader()after open(), but before send().
	ajax.setRequestHeader("Content-type", "text/plain;charset=UTF-8");
	
	//send the http request, with request body specified
	ajax.send(requestBody);
}


//A function to search the hanzi stroke related with the book name. 
//This function is executed when the "searchHanziInBookButton" button is clicked.
//The hanzi_stroke to be search is from inputted textbox. The book_name is from the current selected drop-down list.
function searchHanziStrokeInBook(){
	
	//clear the error message, if there is error message.
	document.getElementById("result").innerHTML = "";
	
	//1. Get the book_name from selected drop-down list.
	var selectedBookName = document.getElementById("bookList").value;
	if(selectedBookName == "initial"){
		document.getElementById("result").innerHTML = "Please select the book before searching hanzi stroke.";
		return;
	}
	
	//2. Get the hanzi_stroke from input box
	var searchHanziStroke = document.getElementById("searchHanziStroke").value;
	if(searchHanziStroke == null){
		document.getElementById("result").innerHTML = "Please input Hanzi Stroke.";
		return;
	}
	if(isEmpty(searchHanziStroke)){
		document.getElementById("result").innerHTML = "Please input Hanzi Stroke.";
		return;
	}
	
	//3. Prepare the request body.
	var requestBody = selectedBookName + ":" + searchHanziStroke;
	
	//===4. Send request to BookController.searchHanziStrokeInBook()===
	
	//make request to Hanziserver using ajax.
	//ajax programming use XMLHttpRequest().
	var ajax = new XMLHttpRequest();
	
	//must use this block code, to read the http response body:
	ajax.onreadystatechange = function() {
		//When readyState property is 4 and the status property is 200, the response is ready:
		if (this.readyState == 4 && this.status == 200) {
			
			//===Get the response from server and validate it===
			//Example responseString: {"book_name":"第二書", "hanzi_stroke_data":[{"hanzi_stroke":"消防局", "page_number":"一", "created_date":"1492318783895"},{"hanzi_stroke":"消防局", "page_number":"三十", "created_date":"1502318783895"}]}
			var responseString = ajax.responseText;
			
			if (responseString === "The request body cannot be read.") {
				document.getElementById("result").innerHTML = "Error: Server cannot procees the request.";
				return;
			}
			if (responseString === "The request body cannot be empty.") {
				document.getElementById("result").innerHTML = "Error: Server cannot procees the request.";
				return;
			}
			if (responseString === "The request body must contain colon character."){
				document.getElementById("result").innerHTML = "Error: Server cannot procees the request.";
				return;
			}
			if (responseString === "The inputted String is not match the requirement."){
				document.getElementById("result").innerHTML = "Error: Server cannot procees the request.";
				return;
			}
			if (responseString === "Not found."){
				document.getElementById("result").innerHTML = "Not found.";
				return;
			}
			if (responseString === "Error when searching hanzi strokes in specified book name."){
				document.getElementById("result").innerHTML = "Error when searching all hanzi stroke in book name.";
				return;
			}
			//================================
			
			
			//convert from string into javasript object:
			var responseObject = JSON.parse(responseString);
			
			//===Get the "hanzi_stroke", "page_number" and "created_date" from json, and display it===
			
			//make sure the book_name response is same as book_name selected from drop-down list.
			if(responseObject.book_name !== selectedBookName){
				document.getElementById("result").innerHTML = "Error: The response hanzi stroke is not related with the selected book name.";
				return;
			}
			
			//prepare the result variable.
			var result = "";
			
			//iterate over the object, to get the hanzi_stroke and created_date
			for (i in responseObject.hanzi_stroke_data){
				
				//get the hanzi_stroke from array inside object
				var hanzi_stroke = responseObject.hanzi_stroke_data[i].hanzi_stroke;
				
				//get the page_number from array inside object
				var page_number = responseObject.hanzi_stroke_data[i].page_number;
				
				//get the created_date from array inside object
				var created_date_epoch_string = responseObject.hanzi_stroke_data[i].created_date;
				
				//convert the created_date_epoch into javascript date.
				var created_date_epoch_int = parseInt(created_date_epoch_string);
				var created_date = new Date(created_date_epoch_int);
				
				//prepare the data
				result = result + hanzi_stroke + "， " + page_number + "頁， " + created_date + "<br/>";
				
				
				//display it inside tag <div id="result"></div>
				document.getElementById("result").innerHTML = result;
			}
			//=====================
			
			
			//================================
		}
	}
	
	//setting the http request.
	ajax.open("POST", "http://localhost:9097/learn-hanzi/api/book/searchHanziStrokeInBook",
			true);
	
	//You must call setRequestHeader()after open(), but before send().
	ajax.setRequestHeader("Content-type", "text/plain;charset=UTF-8");
	
	//send the http request, with request body specified
	ajax.send(requestBody);
	
	//================================
	
	
}


//A function to add the book_name, hanzi_stroke, and page_number to database.
//This function is executed when the "addHanziInBookButton" button is clicked.
//book_name is from currently selected book_name from drop-down list.
//hanzi_stroke and page_number is from the inputted text box.
function insertHanziStrokeInBook(){
	
	//clear the error message, if there is error message.
	document.getElementById("result").innerHTML = "";
	
	//1. Get the book_name from selected drop-down list.
	var selectedBookName = document.getElementById("bookList").value;
	if(selectedBookName == "initial"){
		document.getElementById("result").innerHTML = "Please select the book before searching hanzi stroke.";
		return;
	}
	
	//2. Get the hanzi_stroke from input box.
	var inputHanziStroke = document.getElementById("inputHanziStroke").value;
	if(inputHanziStroke == null){
		document.getElementById("result").innerHTML = "Please input Hanzi Stroke.";
		return;
	}
	if(isEmpty(inputHanziStroke)){
		document.getElementById("result").innerHTML = "Please input Hanzi Stroke.";
		return;
	}
	
	//3. Get the page_number from input box.
	var inputPageNumber = document.getElementById("inputPageNumber").value;
	if(inputPageNumber == null){
		document.getElementById("result").innerHTML = "Please input Page Number.";
		return;
	}
	if(isEmpty(inputPageNumber)){
		document.getElementById("result").innerHTML = "Please input Page Number.";
		return;
	}
	
	//4. Prepare the request body.
	var requestBody = selectedBookName + ":" + inputHanziStroke + ":" + inputPageNumber;
	
	//===5. Send request to BookController.insertHanziStrokeInBook()===
	
	//make request to Hanziserver using ajax.
	//ajax programming use XMLHttpRequest().
	var ajax = new XMLHttpRequest();
	
	//must use this block code, to read the http response body:
	ajax.onreadystatechange = function() {
		//When readyState property is 4 and the status property is 200, the response is ready:
		if (this.readyState == 4 && this.status == 200) {
			
			//===Get the response from server and validate it===
			var responseString = ajax.responseText;
			
			if (responseString === "The request body cannot be read.") {
				document.getElementById("result").innerHTML = "Error: Server cannot procees the request.";
				return;
			}
			if (responseString === "The request body cannot be empty.") {
				document.getElementById("result").innerHTML = "Error: Server cannot procees the request.";
				return;
			}
			if (responseString === "The request body must contain colon character."){
				document.getElementById("result").innerHTML = "Error: Server cannot procees the request.";
				return;
			}
			if (responseString === "The inputted String is not match the requirement."){
				document.getElementById("result").innerHTML = "Error: Server cannot procees the request.";
				return;
			}
			if (responseString === "Error when inserting hanzi stroke."){
				document.getElementById("result").innerHTML = "Error when inserting hanzi stroke.";
				return;
			}
			//================================
			
			
			//convert from string into javasript object:
			var responseObject = JSON.parse(responseString);
			
			//===Get the "hanzi_stroke", "page_number" and "created_date" from json, and display the success message===
			
			//make sure the book_name response is same as book_name selected from drop-down list.
			if(responseObject.book_name !== selectedBookName){
				document.getElementById("result").innerHTML = "Error: The inserted hanzi stroke is not related with the selected book name.";
				return;
			}
			
			//prepare the result variable.
			var result = "";
			
			//iterate over the object, to get the hanzi_stroke and created_date
			for (i in responseObject.hanzi_stroke_data){
				
				//get the hanzi_stroke from array inside object
				var hanzi_stroke = responseObject.hanzi_stroke_data[i].hanzi_stroke;
				
				//get the page_number from array inside object
				var page_number = responseObject.hanzi_stroke_data[i].page_number;
				
				//get the created_date from array inside object
				var created_date_epoch_string = responseObject.hanzi_stroke_data[i].created_date;
				
				//convert the created_date_epoch into javascript date.
				var created_date_epoch_int = parseInt(created_date_epoch_string);
				var created_date = new Date(created_date_epoch_int);
				
				//prepare the data
				result = result + hanzi_stroke + "， " + page_number + "頁， " + created_date + " Added !" + "<br/>";
				
				
				//display it inside tag <div id="result"></div>
				document.getElementById("result").innerHTML = result;
			}
			//=====================
			
			
			//================================
		}
	}
	
	//setting the http request.
	ajax.open("POST", "http://localhost:9097/learn-hanzi/api/book/insertHanziStrokeInBook",
			true);
	
	//You must call setRequestHeader()after open(), but before send().
	ajax.setRequestHeader("Content-type", "text/plain;charset=UTF-8");
	
	//send the http request, with request body specified.
	ajax.send(requestBody);
	
	//=================================================================
}


//A function to validate the String.
function isEmpty(input) {

	//remove all whitespace from input
	removedWhiteSpaceInput = input.replace(/\s+/g, "");

	//if input contains no character left after the whitespace removed, it is then an empty string
	if (removedWhiteSpaceInput.length === 0) {
		return true;
	} else {
		return false;
	}
}