import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Cookies from 'js-cookie';
import 'bootstrap/dist/css/bootstrap.min.css';
import Button from 'react-bootstrap/Button';


function LoginForm() {
  const [login, setUsername] = useState('');
  const [pw, setPassword] = useState('');

  const handleUsernameChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setUsername(e.target.value);
  }

  const handlePasswordChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value);
  }

  useEffect(() => {
    const TGC = Cookies.get('TGC');
    if (TGC) {
      const service = window.location.href;
      const fetchData = async () => {
        try {
          const response = await axios.get(`http://35.247.95.83:9090/sso/st${service.includes('?') ? service.substring(service.indexOf('?')) : ''}`, { withCredentials: true });
          console.log(response.data.location+'?ST='+response.data.ST+'&login='+response.data.login);
          window.location.href = response.data.location+'?ST='+response.data.ST+'&login='+response.data.login;
        } catch (error) {
          console.error(error);
        }
      };
      fetchData();
    }
  }, []); 


  
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const service = window.location.href;
      const response = await axios.post(`http://35.247.95.83:9090/sso/login${service.includes('?') ? service.substring(service.indexOf('?')) : ''}`, { login, pw }, { withCredentials: true });
      console.log(response.data.location+'?ST='+response.data.ST+'&login='+response.data.login);
    
      window.location.href = response.data.location+'?ST='+response.data.ST+'&login='+response.data.login;

    } catch (error) {
      console.error(error);
    }
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
        <Button type="submit" variant="primary">登入</Button>
      </form>
    </body>
  );
}

export default LoginForm; 