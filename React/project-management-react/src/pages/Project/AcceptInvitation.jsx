import { Button } from "@/components/ui/button";
import { acceptInvitation } from "@/Redux/Project/Action";
import React from "react";
import { useDispatch } from "react-redux";
import { useLocation, useNavigate } from "react-router-dom";

const AcceptInvitation = () => {
  const location = useLocation();
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const handleAcceptInvitation = () => {
    const urlParams = new URLSearchParams(location.search);
    const token = urlParams.get("token");
    console.log("Token:", token);
    dispatch(acceptInvitation({ token, navigate }));
  };
  return (
    <div className="h-[85vh] flex flex-col justify-center items-center">
      <h1 className="py-5 font-semibold text-xl">
        당신은 이 프로젝트에 초대되었습니다.
      </h1>
      <Button onClick={handleAcceptInvitation}>초대 수락</Button>
    </div>
  );
};

export default AcceptInvitation;
