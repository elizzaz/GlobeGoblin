import React, { useEffect, useState } from "react";
import { Card, List, Typography, Progress, Badge } from "antd";
import axios from "axios";
import CreateChallenge from "./CreateChallenge.js";
import { Link } from "react-router-dom";

const { Title, Text } = Typography;

const Dashboard = () => {
  const [userChallenges, setUserChallenges] = useState([]);
  const [userPoints, setUserPoints] = useState();
  const [userBadges, setUserBadges] = useState([]);
  const [loading, setLoading] = useState(true);

  const axiosInstance = axios.create({
    baseURL: "/api",
    withCredentials: true,
    headers: {
      "Content-Type": "application/json",
      "Access-Control-Allow-Origin": "*",
    },
  });

  const userId = "8";

  const fetchUserData = async () => {
    try {
      // Appel pour récupérer les défis en cours et complétés
      const challengesResponse = await axiosInstance.get(
        `/userChallenges/user/${userId}`
      );
      setUserChallenges(challengesResponse.data);
      console.log("challengesResponse.data", challengesResponse.data);

      //   Appel pour récupérer les points de l'utilisateur
      const userResponse = await axiosInstance.get(`/users/${userId}`);
      setUserPoints(userResponse.data.points);

      //   Appel pour récupérer les badges de l'utilisateur
      const badgesResponse = await axiosInstance.get(`/users/badges/${userId}`);
      setUserBadges(badgesResponse.data.badges);

      setLoading(false);
    } catch (error) {
      console.error("Failed to fetch user data:", error);
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchUserData();
  }, []);

  return (
    <div style={{ padding: "20px" }}>
      <Title level={2} style={{ textAlign: "center" }}>
        Salut vieux goblin 🧌
      </Title>
      <CreateChallenge />
      {loading ? (
        <Text>Loading...</Text>
      ) : (
        <>
          <Card style={{ marginBottom: "20px" }}>
            <Title level={4}>Résumé de l'activité</Title>
            <Text>Points : {userPoints}</Text>
            <Progress percent={(userPoints / 100) * 100} />
            <Title level={5}>Badges</Title>
            <List
              grid={{ gutter: 16, column: 4 }}
              dataSource={userBadges}
              renderItem={(badge) => (
                <List.Item>
                  <Badge count={badge.name} />
                </List.Item>
              )}
            />
          </Card>

          <Card title="Défis en cours" style={{ marginBottom: "20px" }}>
            <List
              dataSource={userChallenges.filter(
                (challenge) => challenge.status === "INPROGRESS"
              )}
              renderItem={(challenge) => (
                <List.Item>
                  <Text>
                    {challenge.challenge.name} -{" "}
                    {challenge.challenge.description}
                  </Text>
                  <a
                    href={challenge.challenge.place.googleMapsUri}
                    target="_blank"
                    rel="noopener noreferrer"
                  >
                    Voir sur Google Maps
                  </a>
                  <br />
                  <Link to={`/challenge/${challenge.id}`}>
                    <button>Voir le défi</button>
                  </Link>
                </List.Item>
              )}
            />
          </Card>

          <Card title="Défis complétés">
            <List
              dataSource={userChallenges.filter(
                (challenge) => challenge.status === "COMPLETED"
              )}
              renderItem={(challenge) => (
                <List.Item>
                  <Text>
                    {challenge.challenge.name} -{" "}
                    {challenge.challenge.description}
                  </Text>
                  <a
                    href={challenge.challenge.place.googleMapsUri}
                    target="_blank"
                    rel="noopener noreferrer"
                  >
                    Voir sur Google Maps
                  </a>
                  <Link to={`/challenge/${challenge.id}`}>
                    <button>Voir le défi</button>
                  </Link>
                </List.Item>
              )}
            />
          </Card>
        </>
      )}
    </div>
  );
};

export default Dashboard;
