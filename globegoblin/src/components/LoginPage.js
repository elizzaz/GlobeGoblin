import React, { useState } from 'react';
import { Form, Input, Button, Tabs } from 'antd';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const { TabPane } = Tabs;

const LoginPage = () => {
  const [emailLog, setEmailLog] = useState('');
  const [passwordLog, setPasswordLog] = useState('');
  const [nameReg, setNameReg] = useState('');
  const [emailReg, setEmailReg] = useState('');
  const [passwordReg, setPasswordReg] = useState('');
  const navigate = useNavigate();


  const axiosInstance = axios.create({
    baseURL: '/api',
    withCredentials: true,
    headers: {
      'Content-Type': 'application/json', 
      'Access-Control-Allow-Origin': '*'
    },
  });

  const handleLogin = async (values) => {
    try {
      const response = await axiosInstance.post('/users/login', {
        email: emailLog,
        password: passwordLog,
      });
      if (response.status === 200) {
          console.log('Login successful:', response.data);
          navigate('/dashboard');
      }
    } catch (error) {
      console.error('Login failed:', error);
    }
  };

  const handleRegister = async (values) => {
    try {
      const response = await axiosInstance.post('/users/register', {
        email: emailReg,
        name: nameReg,
        password: passwordReg,
      });

      console.log('Registration successful:', response.data);
      navigate('/dashboard');
    } catch (error) {
      console.error('Registration failed:', error);
    }
  };

  return (
    <div id="login-register" style={{ maxWidth: 400, margin: 'auto', padding: '50px' }}>
      <h1 style={{ textAlign: 'center' }}>Prêt à être un Globe Goblin ? 😈</h1>
      <Tabs defaultActiveKey="1">
        <TabPane tab="Connexion" key="1">
          <Form layout="vertical" onFinish={handleLogin}>
            <Form.Item
              label="Email"
              rules={[{ required: true, message: 'Veuillez entrer votre email !' }]}
            >
              <Input
                type="email"
                placeholder="Enter your email"
                value={emailLog}
                onChange={(e) => setEmailLog(e.target.value)}
              />
            </Form.Item>
            <Form.Item
              label="Mot de passe"
              rules={[{ required: true, message: 'Veuillez entrer votre mot de passe !' }]}
            >
              <Input.Password
                placeholder="Enter your password"
                value={passwordLog}
                onChange={(e) => setPasswordLog(e.target.value)}
              />
            </Form.Item>
            <Form.Item>
              <Button type="primary" htmlType="submit" block>
                Se connecter
              </Button>
            </Form.Item>
          </Form>
        </TabPane>
        <TabPane tab="Inscription" key="2">
          <Form layout="vertical" onFinish={handleRegister}>
            <Form.Item
              label="Nom"
              rules={[{ required: true, message: 'Veuillez entrer votre nom !' }]}
            >
              <Input
                placeholder="Enter your name"
                value={nameReg}
                onChange={(e) => setNameReg(e.target.value)}
              />
            </Form.Item>
            <Form.Item
              label="Email"
              rules={[{ required: true, message: 'Veuillez entrer votre email !' }]}
            >
              <Input
                type="email"
                placeholder="Enter your email"
                value={emailReg}
                onChange={(e) => setEmailReg(e.target.value)}
              />
            </Form.Item>
            <Form.Item
              label="Mot de passe"
              rules={[{ required: true, message: 'Veuillez entrer votre mot de passe !' }]}
            >
              <Input.Password
                placeholder="Enter your password"
                value={passwordReg}
                onChange={(e) => setPasswordReg(e.target.value)}
              />
            </Form.Item>
            <Form.Item>
              <Button type="primary" htmlType="submit" block>
                S'inscrire
              </Button>
            </Form.Item>
          </Form>
        </TabPane>
      </Tabs>
    </div>
  );
};

export default LoginPage;

