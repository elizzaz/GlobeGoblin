import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { Card, Typography, Spin, Button, Modal } from "antd";
import { ArrowLeftOutlined } from "@ant-design/icons";
import axios from "axios";


const { Title, Text } = Typography;

const ChallengeDetail = () => {
  const { id } = useParams();
  const [userChallenge, setUserChallenge] = useState(null);
  const [loading, setLoading] = useState(true);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const navigate = useNavigate();
  const DefaultImage = `${process.env.PUBLIC_URL}/defaultImage.jpeg`;

  const axiosInstance = axios.create({
    baseURL: "/api",
    withCredentials: true,
    headers: {
      "Content-Type": "application/json",
      "Access-Control-Allow-Origin": "*",
    },
  });

  useEffect(() => {
    const fetchUserChallenge = async () => {
      try {
        const response = await axiosInstance.get(`/userChallenges/${id}`);
        setUserChallenge(response.data);
        setLoading(false);
      } catch (error) {
        console.error("Erreur lors de la récupération du défi :", error);
        setLoading(false);
      }
    };

    fetchUserChallenge();
  }, [id]);

  const handleValidate = async () => {
    try {
      await axiosInstance.put(`/userChallenges/completeChallenge/${id}/COMPLETED`);
      // Ici, tu peux ajouter une animation de succès
      Modal.success({
        title: 'Bravo !',
        content: 'Défi validé avec succès !',
        onOk: () => navigate("/dashboard"),
      });
    } catch (error) {
      console.error("Erreur lors de la validation du défi :", error);
    }
  };

  if (loading) {
    return <Spin />;
  }

  if (!userChallenge) {
    return <Text>Défi introuvable</Text>;
  }

  return (
    <div style={{ padding: "20px" }}>
      <Button
        type="link"
        icon={<ArrowLeftOutlined />}
        onClick={() => navigate("/dashboard")}
        style={{ float: "right", marginBottom: "20px" }}
      >
        Retour
      </Button>
      <Card>
        <Title level={3}>{userChallenge.challenge.name}</Title>
        <Text>Description : {userChallenge.challenge.description}</Text>
        <br />
        <Text>Score : {userChallenge.challenge.score}</Text>
        <br />
        <Text>Statut : {userChallenge.status}</Text>
        <br />
        <Text>Lieu : {userChallenge.challenge.place.displayNameOfContentChallenge}</Text>
        <br />
        <a
          href={userChallenge.challenge.place.googleMapsUri}
          target="_blank"
          rel="noopener noreferrer"
        >
          Voir sur Google Maps
        </a>
        <br />
        <Button
          type="primary"
          onClick={() => setIsModalVisible(true)}
          style={{ marginTop: "20px" }}
        >
          Upload la photo et valide le défi
        </Button>
      </Card>

      <Modal
        title="Upload la photo"
        visible={isModalVisible}
        onCancel={() => setIsModalVisible(false)}
        footer={null}
      >
        <img
          src={DefaultImage}
          alt="par défaut"
          style={{ width: "100%", marginBottom: "20px" }}
        />
        <Button
          type="primary"
          onClick={handleValidate}
        >
          Valider
        </Button>
      </Modal>
    </div>
  );
};

export default ChallengeDetail;
