import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Cookies from 'js-cookie';
import 'bootstrap/dist/css/bootstrap.min.css';
import Button from 'react-bootstrap/Button';


function Register() {
  const [login, setUsername] = useState('');
  const [pw, setPassword] = useState('');

  const handleUsernameChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setUsername(e.target.value);
  }

  const handlePasswordChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value);
  }
  
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    axios.post(`http://35.247.95.83:9090/sso/register`, { login, pw }, { withCredentials: true }).then((response) => {
      alert('註冊成功')
    }).catch((error)=>{
      alert('註冊失敗')
    });
  }

  return (
    <body>
    <form onSubmit={handleSubmit}>
      <div className="form-group col-4 ">
        <label htmlFor="username">帳號:</label>
        <input type="text" className="form-control" id="username" value={login} onChange={handleUsernameChange} />
      </div>
      <div className="form-group col-4">
        <label htmlFor="password">密碼:</label>
        <input type="password" className="form-control" id="password" value={pw} onChange={handlePasswordChange} />
      </div>
      <Button type="submit" variant="primary">註冊</Button>
    </form>
    </body>
    
  );
}

export default Register; 