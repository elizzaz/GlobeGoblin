import React, { useState } from 'react';
import { Form, Input, Button, Tabs, Typography } from 'antd';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const { TabPane } = Tabs;
const { Title, Text } = Typography;

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
      'Access-Control-Allow-Origin': '*',
    },
  });

  const handleLogin = async () => {
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

  const handleRegister = async () => {
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
    <div style={{
      maxWidth: '400px',
      margin: 'auto',
      padding: '50px',
      textAlign: 'center',
      borderRadius: '10px',
    }}>
      <Title level={2} style={{ marginBottom: '40px', color: '#664E4C' }}>
        Prêt à être un Globe Goblin ? 😈
      </Title>
      <Tabs defaultActiveKey="1" centered>
        <TabPane tab={<Text style={{ fontWeight: 'bold', borderBlockColor: "#75892F",  }}>Connexion</Text>} key="1">
          <Form layout="vertical" onFinish={handleLogin}>
            <Form.Item
              label="Email"
              rules={[{ required: true, message: 'Veuillez entrer votre email !' }]}
              style={{ textAlign: 'left' }}
            >
              <Input
                type="email"
                placeholder="Entrez votre email"
                value={emailLog}
                onChange={(e) => setEmailLog(e.target.value)}
                style={{ padding: '10px', borderRadius: '5px' }}
              />
            </Form.Item>
            <Form.Item
              label="Mot de passe"
              rules={[{ required: true, message: 'Veuillez entrer votre mot de passe !' }]}
              style={{ textAlign: 'left' }}
            >
              <Input.Password
                placeholder="Entrez votre mot de passe"
                value={passwordLog}
                onChange={(e) => setPasswordLog(e.target.value)}
                style={{ padding: '10px', borderRadius: '5px' }}
              />
            </Form.Item>
            <Form.Item>
              <Button
                type="primary"
                htmlType="submit"
                block
                style={{
                  backgroundColor: '#664E4C',
                  borderColor: '#664E4C',
                  padding: '10px',
                  borderRadius: '5px',
                  fontSize: '16px',
                }}
              >
                Se connecter
              </Button>
            </Form.Item>
          </Form>
        </TabPane>
        <TabPane tab={<Text style={{ fontWeight: 'bold' }}>Inscription</Text>} key="2">
          <Form layout="vertical" onFinish={handleRegister}>
            <Form.Item
              label="Nom"
              rules={[{ required: true, message: 'Veuillez entrer votre nom !' }]}
              style={{ textAlign: 'left' }}
            >
              <Input
                placeholder="Entrez votre nom"
                value={nameReg}
                onChange={(e) => setNameReg(e.target.value)}
                style={{ padding: '10px', borderRadius: '5px' }}
              />
            </Form.Item>
            <Form.Item
              label="Email"
              rules={[{ required: true, message: 'Veuillez entrer votre email !' }]}
              style={{ textAlign: 'left' }}
            >
              <Input
                type="email"
                placeholder="Entrez votre email"
                value={emailReg}
                onChange={(e) => setEmailReg(e.target.value)}
                style={{ padding: '10px', borderRadius: '5px' }}
              />
            </Form.Item>
            <Form.Item
              label="Mot de passe"
              rules={[{ required: true, message: 'Veuillez entrer votre mot de passe !' }]}
              style={{ textAlign: 'left' }}
            >
              <Input.Password
                placeholder="Entrez votre mot de passe"
                value={passwordReg}
                onChange={(e) => setPasswordReg(e.target.value)}
                style={{ padding: '10px', borderRadius: '5px' }}
              />
            </Form.Item>
            <Form.Item>
              <Button
                type="primary"
                htmlType="submit"
                block
                style={{
                  backgroundColor: '#664E4C',
                  borderColor: '#664E4C',
                  padding: '10px',
                  borderRadius: '5px',
                  fontSize: '16px',
                }}
              >
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
