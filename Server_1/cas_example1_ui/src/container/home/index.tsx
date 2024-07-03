import React, { useEffect, useState } from "react";
import axios from "axios";
import cookie from 'react-cookies';
import "bootstrap/dist/css/bootstrap.min.css";
import { Button } from "react-bootstrap";

const HomePage: React.FC = () => {
  const [data, setData] = useState<string | null>(null);

  useEffect(() => {
    const urlParams = new URLSearchParams(window.location.search);
    const stParam = urlParams.get("ST");
    const loginParam = urlParams.get("login");
    const cookieName = "example1";
    const cookieValue = document.cookie
      .split("; ")
      .find((row) => row.startsWith(cookieName))
      ?.split("=")[1];
    console.log(cookieValue);
    console.log(stParam);

    axios
      .get(
        cookieValue || !stParam
          ? `http://34.81.61.77:9091/home`
          : `http://34.81.61.77:9091/home?ST=${stParam}&login=${loginParam}`,
        {
          withCredentials: true,
        }
      )
      .then((res) => {
        const result = res.data;
        console.log(result);
        if (typeof result === "object" && result.location) {
          cookie.remove(cookieName)
          window.location.href = result.location;
        } else {
          setData(result);
        }
      })
      .catch((err) => {});
  }, []);

  return (
    <div className="container">
      <div className="mt-5">{data ? data : "Loading..."}</div>
      <div className="mt-3">
        <Button
          variant="primary"
          onClick={() => {
            window.location.href = "http://35.234.61.195:80/home";
          }}
        >
          Go to ServerB
        </Button>
      </div>
    </div>
  );
};

export default HomePage;
