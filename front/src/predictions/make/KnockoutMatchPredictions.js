import React, {Component} from 'react';
import LoadingIndicator from '../../common/LoadingIndicator';
import {getKnockoutMatches, makePrediction} from '../../util/APIUtils'
import {withRouter} from 'react-router-dom';
import {Button, Icon, Input, notification, Table} from 'antd';
import {formatDateTime} from "../../util/Helpers";
import {MATCHES_LIST_SIZE} from "../../constants/index";
import './MatchPredictions.css';

class KnockoutMatchPredictions extends Component {
    constructor(props) {
        super(props);
        this.state = {
            matches: [],
            page: 0,
            size: 10,
            totalElements: 0,
            totalPages: 0,
            last: true,
            isLoading: false
        };
        this.loadMatches = this.loadMatches.bind(this);
        this.handleLoadMore = this.handleLoadMore.bind(this);
        this.makePrediction = this.makePrediction.bind(this);
        this.clearData = this.clearData.bind(this);
    }

    loadMatches(page = 0, size = MATCHES_LIST_SIZE) {
        let promise = getKnockoutMatches(page, size);

        if (!promise) {
            return;
        }

        this.setState({
            isLoading: true
        });

        promise
            .then(response => {
                const matches = this.state.matches.slice();
                this.setState({
                    matches: matches.concat(response.content),
                    page: page,
                    size: response.size,
                    totalElements: response.totalElements,
                    totalPages: response.totalPages,
                    last: response.last,
                    isLoading: false
                })
            }).catch(error => {
            this.setState({
                isLoading: false
            });
            notification.error({
                message: 'ZTI',
                description: error.message || 'Przykro nam, coś poszło nie tak. Spróbuj ponownie!'
            })
        });
    }

    makePrediction(predictionData) {
        if (this.isDisabled(predictionData)) {
            notification.error({
                message: 'ZTI',
                description: "Musisz wypełnić wymagane pola"
            })
        } else {
            const preparedData = {
                matchId: predictionData.key,
                resultH: predictionData.result_home,
                resultA: predictionData.result_away,
                knockoutStage: true,
                teamHId: predictionData.home_team_id,
                teamAId: predictionData.away_team_id
            };
            console.log(preparedData);
            let promise = makePrediction(preparedData);

            if (!promise) {
                return;
            }

            this.setState({
                isLoading: true
            });

            promise
                .then(response => {
                    this.setState({
                        isLoading: false
                    });
                    this.clearData();
                }).catch(error => {
                this.setState({
                    isLoading: false
                });
                notification.error({
                    message: 'ZTI',
                    description: error.message || 'Przykro nam, coś poszło nie tak. Spróbuj ponownie!'
                })
            });
        }
    }

    clearData() {
        this.setState({
            matches: [],
            page: 0,
            size: 10,
            totalElements: 0,
            totalPages: 0,
            last: true,
            isLoading: false
        });
        this.loadMatches();
    }

    isDisabled = (record) => {
        return (record.result_home === null || record.result_away === null)
    };

    componentWillMount() {
        this.loadMatches();
    }

    componentWillReceiveProps(nextProps) {
        if (this.props.isAuthenticated !== nextProps.isAuthenticated) {
            // Reset State
            this.setState({
                matches: [],
                page: 0,
                size: 10,
                totalElements: 0,
                totalPages: 0,
                last: true,
                isLoading: false
            });
            this.loadMatches();
        }
    }

    handleLoadMore() {
        this.loadMatches(this.state.page + 1);
    }

    groupBy(list, keyGetter) {
        const map = new Map();
        list.forEach((item) => {
            const key = keyGetter(item);
            const collection = map.get(key);
            if (!collection) {
                map.set(key, [item]);
            } else {
                collection.push(item);
            }
        });
        return map;
    }

    render() {
        const dataSource = [];
        this.state.matches.forEach((match, matchIndex) => {
            let resultH;
            if (match.statsMatch === null || match.statsMatch.resultH === null) {
                resultH = null
            } else {
                resultH = match.statsMatch.resultH;
            }

            let resultA;
            if (match.statsMatch === null || match.statsMatch.resultA === null) {
                resultA = null
            } else {
                resultA = match.statsMatch.resultA;
            }

            let teamH;
            let teamHId;
            if (match.teamH === null) {
                teamH = match.teamHDescription;
                teamHId = null;
            } else {
                teamH = match.teamH.name;
                teamHId = match.teamH.id;
            }

            let teamA;
            let teamAId;
            if (match.teamA === null) {
                teamA = match.teamADescription;
                teamAId = null;
            } else {
                teamA = match.teamA.name;
                teamAId = match.teamA.id;
            }

            dataSource.push({
                key: match.match.id,
                stage: match.stage,
                home_team: teamH,
                home_team_id: teamHId,
                away_team: teamA,
                away_team_id: teamAId,
                result_home: resultH,
                result_away: resultA,
                date: match.match.date,
                finished: match.match.finished
            })
        });

        const filtered = dataSource.filter((match) => {
            return !match.finished
        });
        const grouped = this.groupBy(filtered, match => match.stage);

        const columns = [{
            title: "Gospodarz",
            dataIndex: "home_team",
            key: "home_team",
        }, {
            title: "GG",
            dataIndex: "result_home",
            key: "result_home",
            render: (text, record) => {
                return (<Input
                    size="small"
                    placeholder="Wynik"
                    onChange={(a) => {
                        record.result_home = parseInt(a.target.value, 10);
                    }}
                />)
            }
        }, {
            title: "GP",
            dataIndex: "result_away",
            key: "result_away",
            render: (text, record) => {
                return (<Input
                    size="small"
                    placeholder="Wynik"
                    onChange={(a) => {
                        record.result_away = parseInt(a.target.value, 10)
                    }}
                />)
            }
        }, {
            title: "Przyjezdny",
            dataIndex: "away_team",
            key: "away_team",
        }, {
            title: "Data",
            dataIndex: "date",
            key: "date",
            render: (text, record) => {
                let color;
                if (record.finished === true) {
                    color = "red"
                } else {
                    color = "green";
                }
                return {
                    props: {
                        style: {
                            color: color
                        }
                    },
                    children: <div>{formatDateTime(text)}</div>
                };
            }
        }, {
            title: "Akcja",
            dataIndex: "action",
            key: "action",
            render: (text, record) => {
                return (
                    <Button id="button" type="dashed" onClick={(a) => this.makePrediction(record)}>
                        <Icon type="plus"/>
                    </Button>
                )
            }
        }];

        const matchViews = [];
        grouped.forEach((group, groupIndex) => {
            matchViews.push(
                <div key={groupIndex} className="margin-table">
                    <Table title={a => groupIndex}
                           pagination={false}
                           dataSource={group}
                           columns={columns}
                    /></div>)
        });

        return (
            <div className="matches-container">
                {matchViews}
                {
                    !this.state.isLoading && this.state.matches.length === 0 ? (
                        <div className="no-matches-found">
                            <span>Nie znaleziono meczy</span>
                        </div>
                    ) : null
                }
                {
                    !this.state.isLoading && !this.state.last ? (
                        <div className="load-more-matches">
                            <Button type="dashed" onClick={this.handleLoadMore} disabled={this.state.isLoading}
                                    htmlType="button">
                                <Icon type="plus"/> Wczytaj więcej
                            </Button>
                        </div>) : null
                }
                {
                    this.state.isLoading ?
                        <LoadingIndicator/> : null
                }
            </div>
        );
    }
}

export default withRouter(KnockoutMatchPredictions)