import React, {Component} from 'react';
import LoadingIndicator from '../../common/LoadingIndicator';
import {getPredictions} from '../../util/APIUtils'
import {withRouter} from 'react-router-dom';
import {Button, Icon, notification, Table} from 'antd';
import {formatDateTime} from "../../util/Helpers";
import {MATCHES_LIST_SIZE} from "../../constants/index";
import './MyMatchPredictions.css';

class MyGroupMatchPredictions extends Component {
    constructor(props) {
        super(props);
        this.state = {
            predictions: [],
            page: 0,
            size: 10,
            totalElements: 0,
            totalPages: 0,
            last: true,
            isLoading: false
        };
        this.loadPredictions = this.loadPredictions.bind(this);
        this.handleLoadMore = this.handleLoadMore.bind(this);
        this.clearData = this.clearData.bind(this);
    }

    loadPredictions(page = 0, size = MATCHES_LIST_SIZE) {
        let promise = getPredictions(page, size);

        if (!promise) {
            return;
        }

        this.setState({
            isLoading: true
        });

        promise
            .then(response => {
                const predictions = this.state.predictions.slice();
                this.setState({
                    predictions: predictions.concat(response.content),
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

    clearData() {
        this.setState({
            predictions: [],
            page: 0,
            size: 10,
            totalElements: 0,
            totalPages: 0,
            last: true,
            isLoading: false
        });
        this.loadPredictions();
    }

    componentWillMount() {
        this.loadPredictions();
    }

    componentWillReceiveProps(nextProps) {
        if (this.props.isAuthenticated !== nextProps.isAuthenticated) {
            // Reset State
            this.clearData();
        }
    }

    handleLoadMore() {
        this.loadPredictions(this.state.page + 1);
    }

    render() {
        const dataSource = [];
        this.state.predictions.forEach((prediction, predictionIndex) => {

            dataSource.push({
                key: prediction.match.id,
                isGroup: prediction.knockoutStage,
                home_team: prediction.teamH.name,
                away_team: prediction.teamA.name,
                home_team_id: prediction.teamH.id,
                away_team_id: prediction.teamA.id,
                result_home: prediction.resultH,
                result_away: prediction.resultA,
                date: prediction.match.date,
                finished: prediction.match.finished
            })
        });

        const filtered = dataSource.filter((prediction) => {
            return !prediction.isGroup
        });

        const columns = [{
            title: "Gospodarz",
            dataIndex: "home_team",
            key: "home_team",
        }, {
            title: "P GG",
            dataIndex: "result_home",
            key: "result_home",
            render: (text, record) => {
                return {
                    props: {
                        style: {
                            fontWeight: "bold"
                        }
                    },
                    children: <div>{text}</div>
                };
            }
        }, {
            title: "P GP",
            dataIndex: "result_away",
            key: "result_away",
            render: (text, record) => {
                return {
                    props: {
                        style: {
                            fontWeight: "bold"
                        }
                    },
                    children: <div>{text}</div>
                };
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
        }];

        return (
            <div className="matches-container">
                {
                    <Table title={a => "Mecze grupowe"}
                           pagination={false}
                           dataSource={filtered}
                           columns={columns}
                    />
                }
                {
                    !this.state.isLoading && this.state.predictions.length === 0 ? (
                        <div className="no-matches-found">
                            <span>Nie znaleziono predykcji</span>
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

export default withRouter(MyGroupMatchPredictions)