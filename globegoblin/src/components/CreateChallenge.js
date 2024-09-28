import React, { useState } from "react";
import {
  Button,
  Modal,
  Form,
  Select,
  Input,
  Spin,
  Typography,
  message,
} from "antd";
import axios from "axios";

const { Title, Text } = Typography;
const { Option } = Select;

const CreateChallenge = () => {
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [loading, setLoading] = useState(false);
  const [challenge, setChallenge] = useState(null);
  const [formVisible, setFormVisible] = useState(true);
  const userId = "8";

  const showModal = () => {
    setIsModalVisible(true);
    setChallenge(null);
    setFormVisible(true);
  };

  const handleCancel = () => {
    setIsModalVisible(false);
  };

  const axiosInstance = axios.create({
    baseURL: "/api",
    withCredentials: true,
    headers: {
      "Content-Type": "application/json",
      "Access-Control-Allow-Origin": "*",
    },
  });

  const onFinish = async (values) => {
    setLoading(true);
    try {
      const response = await axiosInstance.get(
        `challenges/type/${values.challengeType}`
      );
      setChallenge(response.data);
      console.log(response.data);
      setFormVisible(false);
    } catch (error) {
      console.error("Erreur lors de la récupération du défi :", error);
    } finally {
      setLoading(false);
    }
  };

  const startChallenge = async () => {
    if (!challenge || !userId) return;
    try {
      const response = await axiosInstance.post(
        `/userChallenges/startChallenge/${userId}/${challenge.challengeId}`
      );
      message.success("Défi démarré vieux goblin !");
    } catch (error) {
      message.error("Erreur lors du démarrage du défi.");
      console.error("Erreur lors du démarrage du défi :", error);
    }
  };

  const cancelChallenge = async () => {
    if (!challenge || !userId) return;
    try {
      const response = await axiosInstance.post(
        `/userChallenges/cancelChallenge/${userId}/${challenge.challengeId}`
      );
      message.info("Failed, tu pourras le retenter plus tard.");
      setIsModalVisible(false);
    } catch (error) {
      message.error("Erreur lors de l'annulation du défi.");
      console.error("Erreur lors de l'annulation du défi :", error);
    }
  };

  return (
    <div>
      <div
        style={{
          display: "flex",
          justifyContent: "center",
          marginBottom: "5%",
        }}
      >
        <Button
          type="primary"
          style={{
            backgroundColor: "#75892F",
            borderColor: "#75892F",
            color: "#fff", // White text for better contrast
          }}
          onClick={showModal}
        >
          Chaud pour un défi ?
        </Button>
      </div>

      <Modal
        title="Chaud pour un défi ?"
        visible={isModalVisible}
        onCancel={handleCancel}
        footer={null}
        style={{
          backgroundColor: "#664E4C", // Dark brown background for modal
          color: "#fff", // White text for better readability
        }}
      >
        {formVisible && (
          <Form
            layout="vertical"
            onFinish={onFinish}
            initialValues={{ city: "SYDNEY" }}
            style={{ color: "#fff" }} // White text for form
          >
            <Form.Item
              name="challengeType"
              label="Type de défi"
              rules={[
                {
                  required: true,
                  message: "Veuillez sélectionner un type de défi !",
                },
              ]}
            >
              <Select placeholder="Sélectionner un type de défi" className="custom-select">
                <Option value="NEWFOOD">NEWFOOD</Option>
                <Option value="OUPS">OUPS</Option>
                <Option value="EXPLORATION">EXPLORATION</Option>
                <Option value="BARATHON">BARATHON</Option>
                <Option value="FRISSONS">FRISSONS</Option>
                <Option value="SECRETS">SECRETS</Option>
                <Option value="HISTOIRE">HISTOIRE</Option>
                <Option value="MUSEE">MUSEE</Option>
                <Option value="GRAFFITI">GRAFFITI</Option>
                <Option value="CURIOSITES">CURIOSITES</Option>
              </Select>
            </Form.Item>

            <Form.Item
              name="city"
              label="Nom de la ville"
              rules={[
                {
                  required: true,
                  message: "Veuillez entrer le nom de la ville !",
                },
              ]}
            >
              <Input placeholder="Entrez le nom de la ville" style={{ borderColor: "#75892F" }} />
            </Form.Item>

            <Form.Item>
              <Button
                type="primary"
                htmlType="submit"
                loading={loading}
                style={{
                  backgroundColor: "#75892F",
                  borderColor: "#75892F",
                  color: "#fff", // White text for better contrast
                }}
              >
                Trouver mon défi
              </Button>
            </Form.Item>
          </Form>
        )}

        {loading && <Spin />}

        {challenge && (
          <div>
            <Title level={4} style={{ color: "#664E4C" }}>Votre Défi</Title>
            <Text level={4} style={{ color: "#664E4C", textAlign:"center" }}>{challenge.name}</Text>
            <br /><br />
            <Text style={{ color: "#664E4C" }}>Score : {challenge.score}</Text>
            <br />
            <Text style={{ color: "#664E4C" }}>{challenge.description}</Text>
            <br /> <br />
            <Text style={{ color: "#664E4C" }}>
              Lieu : {challenge.place.displayNameOfContentChallenge}
            </Text>
            <br />
            <a
              href={challenge.place.googleMapsUri}
              target="_blank"
              rel="noopener noreferrer"
              style={{ color: "#75892F" }}
            >
              Voir sur Google Maps
            </a>
            <br />
            {challenge.place.websiteUri && (
              <>
                <br />
                <a
                  href={challenge.place.websiteUri}
                  target="_blank"
                  rel="noopener noreferrer"
                  style={{ color: "#75892F" }}
                >
                  Voir leur site web
                </a>
                <br />
              </>
            )}
            <div style={{ marginTop: "20px", textAlign: "center" }}>
              <Button
                type="primary"
                onClick={startChallenge}
                style={{
                  marginRight: "10px",
                  backgroundColor: "#75892F",
                  borderColor: "#75892F",
                  color: "#fff",
                }}
              >
                Prêt(e) à relever le défi
              </Button>
              <Button
                type="default"
                onClick={cancelChallenge}
                style={{ color: "#664E4C" }}
              >
                Naaan c'est trop pour moi
              </Button>
            </div>
          </div>
        )}
      </Modal>
    </div>
  );
};

export default CreateChallenge;
