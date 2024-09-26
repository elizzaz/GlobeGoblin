import LoginPage from "./components/LoginPage";
import Dashboard from "./components/Dashboard";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import "./App.css";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" exact element={<LoginPage/>} />
        <Route path="/dashboard" element={<Dashboard/>} />
        {/* <Route path="/create-challenge" component={CreateChallenge} />
        <Route path="/challenge/:id" component={ChallengeDetails} />
        <Route path="/profile" component={UserProfile} /> */}
      </Routes>
    </Router>
  );
}

export default App;
