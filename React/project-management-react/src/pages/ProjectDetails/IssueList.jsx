import {
  Card,
  CardContent,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Dialog, DialogTrigger } from "@/components/ui/dialog";
import React from "react";
import IssueCard from "./IssueCard";
import { Button } from "@/components/ui/button";

const IssueList = ({ title, status }) => {
  return (
    <div>
      <Dialog>
        <Card className="w-full md:w-[300px] lg:w-[310px]">
          <CardHeader>
            <CardTitle>{title}</CardTitle>
            <CardContent className="px-2">
              <div className="space-y-2">
                <IssueCard />
              </div>
            </CardContent>
            <CardFooter>
              <DialogTrigger>
                <Button>이슈 만들기</Button>
              </DialogTrigger>
            </CardFooter>
          </CardHeader>
        </Card>
      </Dialog>
    </div>
  );
};

export default IssueList;
