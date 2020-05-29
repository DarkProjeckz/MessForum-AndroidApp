/*
*	@Project_Mess - Project
*	Developers : Arun, Ashif, Dhanesh, Krishna
*
*/

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
////////////////////////////////////////////IMPORTING MODULES/////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 var crypto = require('crypto');
 var uuid = require('uuid');
 var express = require('express');
 var mysql = require('mysql');
 var bodyParser = require('body-parser');
 //const jwt = require('jsonwebtoken');
 var jwt = require('jwt-simple');

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
////////////////////////////////////////////MYSQL DATABASE CONNECTOION//////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 var con = mysql.createConnection({
 	host:'localhost',
 	user:'root',
 	password:'',
 	database:'sample'
 });

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
////////////////////////////////////////////ENCRYPTION FUNCTIONS////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
var genRandomString = function(length) {
	// body...
	return crypto.randomBytes(Math.ceil(length/2))
	.toString('hex')
	.slice(0,length);
};

var sha512 = function(password,salt){
	var hash = crypto.createHmac('sha512',salt);
	hash.update(password);
	var value = hash.digest('hex');
	return{
		salt:salt,
		passwordHash:value
	}
};

function saltHashPassword(userPassword){
	var salt = genRandomString(16);
	var passwordData = sha512(userPassword,salt);
	return passwordData;
}

function checkHashPassword(userPassword,salt)
{
	var passwordData = sha512(userPassword,salt);
	return passwordData;
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
/////////////////////////////////////////////////INITIALIZATION/////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
var app = express();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended:true}));
var curr_user = "";
refreshTokens=[];
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
//////////////////////////////////////////////LOGIN AUTHENTICATION//////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

app.post('/login/',(req,res,next)=>{
	var post_data = req.body;
	var user_password = post_data.password;
	var regs = post_data.reg;
	console.log(regs +"  "+user_password);
	con.query("SELECT * FROM users where rollno=?",[regs],function(err,result,fields){
		console.log("Connected!");
		con.on('error',function(err){
			console.log('[MySQL error]',err);
		});
		if(result && result.length)
		{
			console.log("User Present");
			var salt = result[0].passkey;
			var encrypted_password = result[0].pass;
			var hashed_password = checkHashPassword(user_password,salt).passwordHash;
			if(encrypted_password == hashed_password)
			{
				console.log("Password verified");
				//res.end(JSON.stringify(result[0]));
				const user = {
						id:result[0].id,
                        name: result[0].name,
                        rollno: result[0].rollno,
                        dept:result[0].dept
                    }
				const accessToken = generateAccessToken(user);
				const objToSend = {
						id:result[0].id,
                        rollno:result[0].rollno,
                        name: result[0].name,
                        gender: result[0].gender,
                        batch: result[0].batch,
                        dept: result[0].dept,
                        room: result[0].room,
                        accessToken:accessToken
                    }
                    curr_user = result[0].name;
                    console.log(result[0].name+"["+result[0].id+"] "+"logged in");
                    console.log(objToSend);
                    res.status(200).send(JSON.stringify(objToSend))

			}
			else
				res.status(404).end();
		}
		else
		{
			res.status(405).end();
		}
	});	
});

app.post('/adminlogin/',(req,res,next)=>{
	var post_data = req.body;
	var password = post_data.password;
	var username = post_data.username;
	var user = ["Admin01","Admin02"];
	var pass = ["password1","password2"];
	

	var flag=0;
	if (username == user[0] ) 
	{
		if(password==pass[0])
		{

			const accessToken = generateAccessToken(username);
			const admin = {name: username,accessToken:accessToken};
			res.status(200).send(admin);
			console.log(username+" logged in");
		}
		else
			res.status(401).end();
		flag=1;
	}
	if(username == user[1] ) 
	{
		if(password == pass[1])
		{
			const accessToken = generateAccessToken(username);
			const admin = {name: username,accessToken:accessToken};
			res.status(200).send(admin);
			console.log(username+" logged in");
		}
		else
			res.status(401).end();
		flag=1;
	}
	if(username == user[2] )
	{
		if(password==pass[2])
		{
			const accessToken = generateAccessToken(username);
			const admin = {name: username,accessToken:accessToken};
			res.status(200).send(admin);
			console.log(username+" logged in");
		}
		else
			res.status(401).end();
		flag=1;
	}
	if(flag == 0)
		res.status(404).end();
});

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
////////////////////////////////////////////////////USER////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
app.post("/allusers/",authenticateToken, function(req , res){
                
                console.log(curr_user+" triggered from device");
                con.query("SELECT id,rollno,name,batch,dept,room FROM users order by rollno asc",function(err, recordset) {
            if(err) console.log(err);
            
            if(recordset && recordset.length)
			{
				res.status(200).json(recordset);
			}
			else
			{
				res.status(404).end();	
			}
           //res.end(JSON.stringify(record)); // Result in JSON format
            
            
        });
});

app.post('/register/',authenticateToken,(req,res,next)=>{
	var post_data = req.body;
	
	var name = post_data.name;
	var reg = parseInt(post_data.reg,10);
	console.log(typeof reg);
	var gen = post_data.gen;
	var batch = post_data.batch;
	var room = post_data.room;
	var dept = post_data.dept;

	var plain_password =  post_data.password;
	var hash_data = saltHashPassword(plain_password);
	var password = hash_data.passwordHash;
	var salt = hash_data.salt;
	console.log("Inside Register");
	con.query("SELECT * FROM users where rollno=?",[reg],function(err,result,fields){
		console.log("Connected!");
		con.on('error',function(err){
			console.log('[MySQL error]',err);
		});
		if(result && result.length)
		{
			res.status(404).end();
			console.log("Already Registered ");
			console.log(result[0].name+"  "+result[0].room);
		}
		else
		{
			console.log("Before insert");
			let stmt = `INSERT INTO users (rollno, pass, passkey, name, gender, batch, dept, room) VALUES(?,?,?,?,?,?,?,?)`;
			let todo = [reg,password,salt,name,gen,batch,dept,room];
			con.query('INSERT INTO `users` (`rollno`, `pass`, `passkey`, `name`, `gender`, `batch`, `dept`, `room`) VALUES(?,?,?,?,?,?,?,?)',[reg,password,salt,name,gen,batch,dept,room],function(err,result,fields){
	console.log("Success insert!");
				con.on('error',function(err){
			console.log('[MySQL error]',err);
			//res.json("Registered error ",err);
				});
				
				console.log("Name: "+name);
				console.log("Register No: "+reg);
				console.log("Password: "+password);
				console.log("Salt: "+salt);
				console.log("Room: "+room); 
				console.log("Department: "+dept); 
				console.log("Gender: "+gen); 
				console.log("Batch: "+batch); 
				
			});
			con.query("INSERT INTO stat (roll,statusb,date1) VALUES("+reg+","+0+",'"+dat()+"')",function(err,result)
	            		{
	                         if(err) console.log(err);
	                         //res.status(200).end();
	            		});
			res.status(200).send()
		}
	});

});

app.post("/user/:id/",authenticateToken,function(req , res){
				var userId = req.params.id;
				console.log(req.body+"   "+res);
				console.log("inside user  "+userId);
                console.log(curr_user+" triggered for details");
                con.query("SELECT id,rollno,name,gender,batch,dept,room FROM users where rollno=?",[userId],function(err, result) {
            if(err){
	            console.log(err) ;
	            	
            } 
            if(result && result.length)
			{
				const objToSend = {
           				id:result[0].id,
						rollno:result[0].rollno,
                        name: result[0].name,
                        gender: result[0].gender,
                        batch: result[0].batch,
                        dept: result[0].dept,
                        room: result[0].room
                    }
                   console.log(JSON.stringify(objToSend));
// "{"name":"Skip","age":2,"favoriteFood":"Steak"}"

				console.log(JSON.parse(JSON.stringify(objToSend)));
           //res.json(objToSend); // Result in JSON format
           res.send(JSON.stringify(objToSend));
            //res.json(recordset);
			}
			else
			{
					res.status(404).end();
			}
        });
});

app.post("/deleteusers/:id/",authenticateToken, function(req , res){
                console.log(curr_user+" triggered from device");
        con.query("SELECT * FROM users where id= "+req.params.id,function(err,result,fields){
		console.log("Connected!");
		if(err) console.log(err);
                con.query("DELETE FROM users where id = "+req.params.id,function(err, recordset) {
	            if(err) console.log(err);
	           //res.end(JSON.stringify(record)); // Result in JSON format
        });
        con.query("DELETE FROM stat where id = "+req.params.id,function(err, recordset) {
	            if(err) console.log(err);
	           //res.end(JSON.stringify(record)); // Result in JSON format
	           
        });
        

			con.query("DELETE FROM token where regno = "+result[0].rollno,function(err, recordset) {
		            if(err) console.log(err);
		           //res.end(JSON.stringify(record)); // Result in JSON format
		           res.status(200).end();
	        });
		
		
	});
});

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
/////////////////////////////////////////////////////DASHBOARD//////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
app.post("/data/",authenticateToken,function(req , res){
                console.log(curr_user+" triggered from device");
                con.query("SELECT * FROM menu",function(err, recordset) {
	            if(err) console.log(err);
	           //res.end(JSON.stringify(record)); // Result in JSON format
	            res.json(recordset);
            
        });
});

app.post("/modmeal/",authenticateToken, function(req , res){
				var modId = parseInt(req.body.id);
				var modMeal = req.body.meal;
                console.log(curr_user+" triggered for details");
                if (!((modId >= 1 && modId<=7) || (modId == 11) ||(modId == 22)||(modId == 33)||(modId == 44)||
                	(modId == 55)||(modId == 66)||(modId == 77)||(modId == 111)||(modId == 222)||(modId == 333)||
                	(modId == 444)||(modId == 555)||(modId == 666)||(modId == 777))) 
                {
                	res.status(404).send()
                }
                con.query("UPDATE menu SET food=? WHERE sno=?",[modMeal,modId],function(err, result) {
            
            con.on('error',function(err){
			console.log('[MySQL error]',err);
			//res.json("Registered error ",err);
				});
				res.status(200).send()
			});
});

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
/////////////////////////////////////////////////////TOKEN//////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
app.post("/token/:roll/",authenticateToken, function(req , res){
				var userId = req.params.roll;
                console.log(curr_user+" triggered for details");
                con.query("SELECT * FROM token where regno=?",[userId],function(err, result) {
            if(err) console.log(err);
            if(result && result.length)
			{
	           res.json(result); // Result in JSON format
	            //res.json(recordset);
	        }
	        else
	        {
	        	res.status(404).end();
	        }
            
        });
});

app.post("/token/",authenticateToken, function(req , res){
                console.log(curr_user+" triggered for details");
                con.query("SELECT * FROM token",function(err, result) {
            if(err) console.log(err);
            if(result && result.length)
			{
	           res.json(result); // Result in JSON format
	            //res.json(recordset);
	        }
	        else
	        {
	        	res.status(404).end();
	        }
            
        });
});

app.post("/tokenreg/",authenticateToken, function(req , res){
				var data = req.body;
				var reg = data.reg;
				var name = data.name;
				var egg = data.egg;
				var chicken = data.chicken;
				var mutton = data.mutton;
				var gobi = data.gobi;
				var mushroom = data.mushroom;

				var egg1 = 0;
				var chicken1 =0;
				var mutton1 = 0;
				var gobi1 = 0;
				var mushroom1 = 0;
				var flag22 = 0;
				var answer = "";

				con.query("SELECT * FROM token where regno = "+reg,function(err, result) {
				            if(err) console.log(err);
				            
				            if (result && result.length) 
				            {
				            	var n = 0;
				            	while(n < result.length)
				            	{
				            		if (result[n].tname == "egg") {
				            			egg1++;
				            		}
				            		if (result[n].tname == "chicken") {
				            			chicken1++;
				            		}
				            		if (result[n].tname == "gobi") {
				            			gobi1++;
				            		}
				            		if (result[n].tname == "mutton") {
				            			mutton1++;
				            		}
				            		if (result[n].tname == "mushroom") {
				            			mushroom1++;
				            		}
				            		n++;
				            	}
				            	console.log(egg1+" "+chicken1+" "+mutton1+" "+gobi1+" "+mushroom1);
				            }

				                          console.log(egg1+" "+chicken1+" "+mutton1+" "+gobi1+" "+mushroom1);
				

                console.log(curr_user+" triggered for details");

                if(egg!="")
                {
                	if(egg1 != 0)
                	{
                		answer = answer+"egg\n";
                		flag22 = 1;
                		egg = 0;
                	}
                	else{
                		var e_no = '01';
                		numgen(e_no, function(numgenvalue){
	                	con.query(`INSERT INTO token (regno,name,tname,tnum,toknum) VALUES ('${reg}','${name}','egg','${egg}','${numgenvalue}')`,function(err, result) {
				            if(err) console.log(err);
			    	        });
                 	    });
                	}
                	
                }
                else
                {
                	egg = 0;
                }
                if(chicken!="")
                {
                	if(chicken1 != 0)
                	{
                		answer = answer+"chicken\n";
                		flag22 = 1;
                		chicken = 0;
                	}
                	else
                	{
                	    var c_no = '02';
                    	numgen(c_no, function(numgenvalue){
						con.query(`INSERT INTO token (regno,name,tname,tnum,toknum) VALUES ('${reg}','${name}','chicken','${chicken}','${numgenvalue}')`,function(err, result) {
				            if(err) console.log(err);
			    	        });
                    	});
                	}
                }
                else
                {
                	chicken = 0;
                }
                if(mutton!="")
                {
                	if(mutton1 != 0)
                	{
                		answer = answer+"mutton\n";
                		flag22 = 1;
                		mutton = 0;
                	}
                	else
                	{
                		var m_no = '03';
                     	numgen(m_no, function(numgenvalue){
						con.query(`INSERT INTO token (regno,name,tname,tnum,toknum) VALUES ('${reg}','${name}','mutton','${mutton}','${numgenvalue}')`,function(err, result) {
				            if(err) console.log(err);
				            });
                    	});
                	}
                }
                else
                {
                	mutton = 0;
                }
                if(gobi!="")
                {
                	if(gobi1 != 0)
                	{
                		answer = answer+"gobi\n";
                		flag22 = 1;
                		gobi = 0;
                	}
                	else
                	{
                       	var g_no = '04';
                     	numgen(g_no, function(numgenvalue){
						con.query(`INSERT INTO token (regno,name,tname,tnum,toknum) VALUES ('${reg}','${name}','gobi','${gobi}','${numgenvalue}')`,function(err, result) {
				            if(err) console.log(err);
				            });
                    	});
                	}
                }
                else
                {
                	gobi = 0;
                }
                if(mushroom!="")
                {
                	if(mushroom1 != 0)
                	{
                		answer = answer+"mushroom\n";
                		flag22 = 1;
                		mushroom = 0;
                	}
                	else
                	{
   	                	var mu_no = '05';
                    	numgen(mu_no, function(numgenvalue){
						con.query(`INSERT INTO token (regno,name,tname,tnum,toknum) VALUES ('${reg}','${name}','mushroom','${mushroom}','${numgenvalue}')`,function(err, result) {
		    		            if(err) console.log(err);
			    	        });
                    	});
                	}
                }
                else
                {
                	mushroom = 0;
                }
                con.query("UPDATE tcount SET egg=egg+"+egg+", chicken=chicken+"+chicken+", mushroom=mushroom+"+mushroom+", mutton=mutton+"+mutton+", gobi=gobi+"+gobi+"",function(err, result) {
			            if(err) console.log(err);  
			        });
                var obj1 = {
                	ans : answer
                }
                console.log(answer);
                res.status(200).json(obj1);
                /*if (flag22 == 1)
                {
                	res.status().json(obj1);
                }
                else
                {
                	res.status(200).json(obj1);
                }*/

				          

				        });
                
  
});

app.post("/deletetoken/:id/",authenticateToken, function(req , res){
                console.log(curr_user+" triggered from device");
                con.query("DELETE FROM TOKEN WHERE id = "+req.params.id,function(err, recordset) {
	            if(err) console.log(err);
	           res.status(200).end(); // Result in JSON format
        });
});


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
/////////////////////////////////////////////////////COUNT//////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
app.post("/fetchcount/",authenticateToken, function(req , res){
                console.log(curr_user+" triggered for details");
                
                con.query("SELECT date1 from stat",function(err, result) {
            if(err) console.log(err);
            if(dat() != result[0].date1)
            {
            	console.log(dat()+"     "+result[0].date1);
            	con.query("UPDATE fcount SET breakfast=500,lunch=500,dinner=500",function(err, result) {
           		 if(err) console.log(err);
           		});
            }
            con.query("SELECT * FROM fcount",function(err, result) {
            if(err) console.log(err);
            if(result && result.length)
			{
				var sendData={
					id:result[0].id,
					breakfast:result[0].breakfast,
					lunch:result[0].lunch,
					dinner:result[0].dinner
				}
	           res.json(sendData); // Result in JSON format
	            //res.json(recordset);
	        }
	        else
	        {
	        	res.status(404).end();
	        }
            
        });
    });
});

app.post("/endregistration/",authenticateToken, function(req , res){
                console.log(curr_user+" triggered for details");
                con.query("UPDATE stat SET statusb=1,date1='"+dat()+"'",function(err, result) {
            if(err) console.log(err);
            console.log(result);
            res.status(200).end();
            
        });
});

app.post("/regfood/",authenticateToken, function(req , res){
				var userId = req.body.roll;
				var breakfast = req.body.breakfast;
				var lunch = req.body.lunch;
				var dinner = req.body.dinner;
                console.log(curr_user+" triggered for details");
                console.log(userId+"    "+breakfast+"    "+lunch+"   "+dinner);
                con.query("SELECT * from stat where roll=?",[userId],function(err, result) {
	            if(err) console.log(err);
	            var flag = 1;
	            if(result[0].statusb == 1)
	            {
	            	if(result[0].date1 == dat())
	            	{
	            		flag = 0;
	            		res.status(404).end();
	            	}
	            	else
	            	{
	            		con.query("UPDATE fcount SET breakfast=500,lunch=500,dinner=500",function(err, result)
	            		{
	                         if(err) console.log(err);
	            		});
	            		con.query("UPDATE stat SET statusb=0,date1='"+dat()+"'",function(err, result)
	            		{
	                         if(err) console.log(err);
	            		});
	            	}
	            }
	            else
	            {
	            	if(result[0].date1 != dat())
	            	{
	            		con.query("UPDATE fcount SET breakfast=500,lunch=500,dinner=500",function(err, result)
	            		{
	                         if(err) console.log(err);
	            		});
	            		con.query("UPDATE stat SET statusb=0,date1='"+dat()+"'",function(err, result)
	            		{
	                         if(err) console.log(err);
	            		});
	            	}
	            } 
	            if (flag == 1)
	            {
	            	    con.query("UPDATE stat SET statusb=1 WHERE roll=?",[userId],function(err, result)
	            		{
	                         if(err) console.log(err);
	            		});
	            		con.query("UPDATE fcount SET breakfast=breakfast-"+breakfast,function(err, result)
	            		{
	                         if(err) console.log(err);
	            		});
	            		con.query("UPDATE fcount SET lunch=lunch-"+lunch,function(err, result)
	            		{
	                         if(err) console.log(err);
	            		});
	            		con.query("UPDATE fcount SET dinner=dinner-"+dinner,function(err, result)
	            		{
	                         if(err) console.log(err);
	            		});
	            		res.status(200).end();
	            }   
        });
});

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
//////////////////////////////////////////////////NOTIFICATION//////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
app.post("/addnotification/",authenticateToken, function(req , res){
				var admin = req.body.admin;
				var notification = req.body.notification;
				console.log(admin+"   "+notification);
                console.log(curr_user+" triggered for details");
                con.query("INSERT INTO notification(m_id,message) VALUES ('"+admin+"','"+notification+"');",function(err,result)
	            		{
	                         if(err) console.log(err);
	                         res.status(200).end();
	            		});
});

app.post("/removenotification/",authenticateToken, function(req , res){
                console.log(curr_user+" triggered for details");
                con.query("DELETE FROM notification",function(err,result)
	            		{
	                         if(err) console.log(err);
	                         res.status(200).end();
	            		});
});

app.post("/getnotification/",authenticateToken, function(req , res){
                console.log(curr_user+" triggered for details");
                con.query("SELECT * FROM notification WHERE id=(SELECT max(id) from notification);",function(err,result)
	            		{
	                         if(err) console.log(err);
	                         if(result && result.length)
	                         {
	                         	var obj = {
	                         		message : result[0].message
	                         	}
	                         	//res.status(200).send(JSON.stringify(obj));
	                         	res.status(200).send(obj);
	                         }
	                         else
	                         {
	                         	var obj = {
	                         		message :"No notification"
	                         	}
	                         	res.status(200).send(obj);
	                         }
	            		});
});

app.get('*',(req,res,next)=>{
	res.sendFile(__dirname + '/404.html');
});

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
/////////////////////////////////////////////USER DEFINED FUNCTIONS/////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*function generateAccessToken(user) {
	console.log(process.env.ACCESS_TOKEN_SECRET)
  return jwt.sign(user, process.env.ACCESS_TOKEN_SECRET)
}*/

function generateAccessToken(user) {
    return jwt.encode({
        exp: expiresIn(1),
        user: user,
        //timezoneOffset: timezoneOffset,
        createdAt: new Date().getTime()
    }, 'fc4589e94a2c56166762fa6982109b3aaeecdc03df737e9ed65a1091f486fac69ada3efa546e6302fd568b0c5960a938b9a213a666e190d6afc884a36bf6d01a');
}

function expiresIn(numDays) {
    var dateObj = new Date();
    return dateObj.setDate(dateObj.getDate() + numDays);
}

function authenticateToken(req, res, next) {
	console.log("Inside authorization");
  const authHeader = req.headers['authorization']
  const token = authHeader && authHeader.split(' ')[1]
  if (token == null) return res.sendStatus(401)

  var decoded = jwt.decode(token,'fc4589e94a2c56166762fa6982109b3aaeecdc03df737e9ed65a1091f486fac69ada3efa546e6302fd568b0c5960a938b9a213a666e190d6afc884a36bf6d01a' );
	if(!decoded) res.sendStatus(403);
	const loggedUser = decoded.user;
	console.log(loggedUser);
    next()
}

/*function authenticateToken(req, res, next) {
  const authHeader = req.headers['authorization']
  const token = authHeader && authHeader.split(' ')[1]
  if (token == null) return res.sendStatus(401)

  jwt.verify(token, process.env.ACCESS_TOKEN_SECRET, (err, user) => {
    if (err) return res.sendStatus(403)
    req.user = user
    next()
  })
}*/

function numgen(id,call)
{
	var name = {"01":"egg", "02":"chicken", "03":"mutton","04":"mushroom","05":"gobi"};
	var tok;
	con.query("SELECT "+name[id]+" FROM tcount",function(err,result){
	                        if(err) console.log(err);
	                        if(result && result.length)
	                        {
	                        	if(id=="01")
									tok = result[0].egg;
								else if(id=="02")
									tok = result[0].chicken;
								else if(id=="03")
									tok = result[0].mutton;
								else if(id=="04")
									tok = result[0].mushroom;
								else if(id=="05")
									tok = result[0].gobi;
								call(datee()+id+(tok));
	                        	
	                        }
	                       
	            		});
	
}

function  dat()
{
	var d = new Date();
	var date = ("0"+d.getDate()).slice(-2);
	var month = ("0"+(d.getMonth()+1)).slice(-2);
	var year = d.getFullYear();
	return(year+"-"+month+"-"+date);
}
function  datee()
{
	var d = new Date();
	var date = ("0"+d.getDate()).slice(-2);
	var month = ("0"+(d.getMonth()+1)).slice(-2);
	var year = d.getFullYear();
	return((""+year).slice(-2)+month+date);
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
/////////////////////////////////////////////////////SERVER STARTUP/////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
app.listen(process.env.PORT || 3000,()=>{ console.log('Project_Mess server running....'); });