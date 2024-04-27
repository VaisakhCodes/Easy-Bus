from flask import *
from database import *

public=Blueprint('public',__name__)

@public.route('/')
def home():
	return render_template('home.html')

@public.route('/login',methods=['get','post'])
def login():
	if 'submit' in request.form:
		uname=request.form['username']
		passs=request.form['password']
		q="select * from login where username='%s' and password='%s'"%(uname,passs)
		res=select(q);
		print(res)
		if res:
			if res[0]['usertype']=="admin":
				return redirect(url_for('admin.adminhome'))
		else:
			flash("InvalidUsernameorPassword")
				# if res[0]['usertype']=="customer":
				# 	return redirect(url_for('customer.customerhome'))

	return render_template('login.html')
@public.route('/register',methods=['get','post'])
def register():
	if 'submit' in request.form:
			fname=request.form['first_name']
			lname=request.form['last_name']
			agee=request.form['age']
			genderr=request.form['gender']
			phnno=request.form['phone']
			emailid=request.form['email']
			uname=request.form['username']
			passs=request.form['password']
			q="insert into login values(null,'%s','%s','customer')" %(uname,passs)
			id=insert(q)
			q="insert into customer values(null,'%s','%s','%s','%s','%s','%s','%s')" %(id,fname,lname,agee,genderr,phnno,emailid)
			insert(q)
			return redirect(url_for('public.login'))

	return render_template('register.html')


