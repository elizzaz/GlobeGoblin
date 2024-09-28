import React, { useEffect, useState } from "react";
import { Card, List, Typography, Progress, Flex, Button, Steps } from "antd";
import axios from "axios";
import CreateChallenge from "./CreateChallenge.js";
import { Link } from "react-router-dom";

const { Title, Text } = Typography;

const Dashboard = () => {
  const [userChallenges, setUserChallenges] = useState([]);
  const [userPoints, setUserPoints] = useState();
  const [userBadges, setUserBadges] = useState([]);
  const [loading, setLoading] = useState(true);
  const [progress, setProgress] = useState(0);
  const [nextBadgeThreshold, setNextBadgeThreshold] = useState(0);

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

      const response = await axiosInstance.get(
        `/badges/prochainBadge/${userId}`
      );
      console.log("response Badge", response.data);
      setProgress(response.data.progressPercentage);
      setNextBadgeThreshold(response.data.nextBadge);

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
          <Card style={{ marginBottom: "20px", borderColor: "#664E4C" }}>
            <div style={{ textAlign: "center" }}>
              <Title style={{ color: "#664E4C" }} level={4}>
                Résumé de l'activité
              </Title>
              <Text style={{ color: "#664E4C" }}>Points : {userPoints}</Text>
              <Flex
                gap="small"
                wrap
                style={{
                  paddingLeft: "30%",
                  paddingTop: "10%",
                  paddingBottom: "10%",
                }}
              >
                <Progress
                  strokeColor="#75892F"
                  trailColor="#F1F1F1"
                  type="circle"
                  percent={progress}
                />
              </Flex>
              <Text style={{ color: "#664E4C" }}>
                Prochain badge : <br></br> {nextBadgeThreshold}
              </Text>
              <Title level={5} style={{ color: "#664E4C" }}>
                Badges
              </Title>
            </div>
            <Steps
            colorPrimary="#75892F"
            colorPrimaryBorder="#75892F"
              direction="horizontal"
              size="small"
              current={userPoints}
              items={userBadges.map((badge) => ({
                title: badge.name,
                description: badge.description,
              }))}
            />
          </Card>

          <Card
            title="Défis en cours"
            style={{
              marginBottom: "20px",
              borderColor: "#664E4C",
            }}
          >
            <List
              dataSource={userChallenges.filter(
                (challenge) => challenge.status === "INPROGRESS"
              )}
              renderItem={(challenge) => (
                <List.Item>
                  <Title level={4}>{challenge.challenge.name}</Title>
                  <Text style={{ color: "#664E4C", margin: "5px" }}>
                    {challenge.challenge.description}
                  </Text>
                  <a
                    href={challenge.challenge.place.googleMapsUri}
                    target="_blank"
                    rel="noopener noreferrer"
                    style={{ color: "#75892F" }}
                  >
                    Voir sur Google Maps
                  </a>
                  <br />
                  <Link to={`/challenge/${challenge.id}`}>
                    <Button
                      type="primary"
                      style={{
                        backgroundColor: "#664E4C",
                        borderColor: "#664E4C",
                        margin: "20px",
                      }}
                    >
                      Voir le défi
                    </Button>
                  </Link>
                </List.Item>
              )}
            />
          </Card>

          <Card
            style={{
              borderColor: "#664E4C",
            }}
            title="Défis complétés"
          >
            <List
              dataSource={userChallenges.filter(
                (challenge) => challenge.status === "COMPLETED"
              )}
              renderItem={(challenge) => (
                <List.Item>
                    <Title level={4}>{challenge.challenge.name}</Title>
                  <Text style={{ color: "#664E4C", margin: "5px" }}>
                    {challenge.challenge.description}
                  </Text>
                  <a
                    href={challenge.challenge.place.googleMapsUri}
                    target="_blank"
                    rel="noopener noreferrer"
                    style={{ color: "#75892F" }}
                  >
                    Voir sur Google Maps
                  </a>
                  <Link to={`/challenge/${challenge.id}`}>
                    <Button
                      type="primary"
                      style={{
                        backgroundColor: "#664E4C",
                        borderColor: "#664E4C",
                        margin: "20px",
                      }}
                    >
                      Voir le défi
                    </Button>{" "}
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
