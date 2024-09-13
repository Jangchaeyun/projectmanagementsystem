import { Button } from "@/components/ui/button";
import { Card } from "@/components/ui/card";
import {
  getUserSubscription,
  upgradeSubscription,
} from "@/Redux/Subscription/Action";
import { CheckCircledIcon } from "@radix-ui/react-icons";
import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";

const UpgradeSuccess = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const { subscription } = useSelector((store) => store);

  const queryParams = new URLSearchParams(location.search);

  const paymentId = queryParams.get("payment_id");
  const planType = queryParams.get("planType");

  useEffect(() => {
    dispatch(upgradeSubscription({ planType }));
    dispatch(getUserSubscription());
  }, []);

  return (
    <div className="flex justify-center">
      <Card className="mt-20 p-5 space-y-5 flex flex-col items-center">
        <div className="flex items-center gap-4">
          <CheckCircledIcon className="h-9 w-9 text-green-500" />
          <p className="text-xl">플랜 업그레이드 성공</p>
        </div>
        <div className="space-y-3">
          <p className="text-green-500">
            시작 날짜: {subscription.userSubscription?.subscriptionStartDate}
          </p>
          <p className="text-red-500">
            종료 날짜: {subscription.userSubscription?.subscriptionEndDate}
          </p>
          <p className="">
            플랜 타입: {subscription.userSubscription?.planType}
          </p>
        </div>
        <Button onClick={() => navigate("/")}>홈으로 돌아가기</Button>
      </Card>
    </div>
  );
};

export default UpgradeSuccess;
