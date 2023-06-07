import React from "react";
import { Container, CardGroup } from "react-bootstrap";
import { useParams } from "react-router-dom";

import BasicLayout from "main/layouts/BasicLayout/BasicLayout";
import CommonsOverview from "main/components/Commons/CommonsOverview";
import CommonsPlay from "main/components/Commons/CommonsPlay";
import FarmStats from "main/components/Commons/FarmStats";
import ManageCows from "main/components/Commons/ManageCows";
import Profits from "main/components/Commons/Profits";
import { useBackend, useBackendMutation } from "main/utils/useBackend";
import { useCurrentUser } from "main/utils/currentUser";
import Background from "../../assets/PlayPageBackground.png";

export default function PlayPage() {
    const { commonsId } = useParams();
    const { data: currentUser } = useCurrentUser();

    // Stryker disable all
    const { data: userCommons } = useBackend(
        [`/api/usercommons/forcurrentuser?commonsId=${commonsId}`],
        {
            method: "GET",
            url: "/api/usercommons/forcurrentuser",
            params: {
                commonsId: commonsId,
            },
        }
    );
    // Stryker restore all

    // Stryker disable all
    const { data: cowLots } = useBackend(
        [`/api/cowlots/forcurrentuser?commonsId=${commonsId}`],
        {
            method: "GET",
            url: "/api/cowlots/forcurrentuser",
            params: {
                commonsId: commonsId,
            },
        }
    );
    // Stryker restore all

    // Stryker disable all
    const { data: commons } = useBackend([`/api/commons?id=${commonsId}`], {
        method: "GET",
        url: "/api/commons",
        params: {
            id: commonsId,
        },
    });
    // Stryker restore all

    // Stryker disable all
    const { data: userCommonsProfits } = useBackend(
        [`/api/profits/all/commonsid?commonsId=${commonsId}`],
        {
            method: "GET",
            url: "/api/profits/all/commonsid",
            params: {
                commonsId: commonsId,
            },
        }
    );
    // Stryker restore all

    const onSuccessBuy = () => {};

    // Stryker disable all
    const objectToAxiosParamsBuy = (newUserCommons) => ({
        url: "/api/usercommons/buy",
        method: "PUT",
        data: newUserCommons,
        params: {
            commonsId: commonsId,
        },
    });
    // Stryker restore all

    // Stryker disable all
    const mutationbuy = useBackendMutation(
        objectToAxiosParamsBuy,
        { onSuccess: onSuccessBuy },
        // Stryker disable next-line all : hard to set up test for caching
        [`/api/usercommons/forcurrentuser?commonsId=${commonsId}`]
    );
    // Stryker restore all

    const onBuy = (userCommons) => {
        mutationbuy.mutate(userCommons);
    };

    const onSuccessSell = () => {};

    // Stryker disable all
    const objectToAxiosParamsSell = (newUserCommons) => ({
        url: "/api/usercommons/sell",
        method: "PUT",
        data: newUserCommons,
        params: {
            commonsId: commonsId,
        },
    });
    // Stryker restore all

    // Stryker disable all
    const mutationsell = useBackendMutation(
        objectToAxiosParamsSell,
        { onSuccess: onSuccessSell },
        [`/api/usercommons/forcurrentuser?commonsId=${commonsId}`]
    );
    // Stryker restore all

    const onSell = (userCommons) => {
        mutationsell.mutate(userCommons);
    };

    return (
        <div
            data-testid="background"
            style={{
                backgroundSize: "cover",
                backgroundImage: `url(${Background})`,
            }}
        >
            <BasicLayout>
                <Container>
                    {!!currentUser && <CommonsPlay currentUser={currentUser} />}
                    {!!commons && (
                        <CommonsOverview
                            commons={commons}
                            currentUser={currentUser}
                        />
                    )}
                    <br />
                    {!!userCommons && !!cowLots && (
                        <CardGroup>
                            <ManageCows
                                userCommons={userCommons}
                                commons={commons}
                                onBuy={onBuy}
                                onSell={onSell}
                            />
                            <FarmStats
                                userCommons={userCommons}
                                cowLots={cowLots}
                            />
                            <Profits
                                userCommons={userCommons}
                                profits={userCommonsProfits}
                            />
                        </CardGroup>
                    )}
                </Container>
            </BasicLayout>
        </div>
    );
}
