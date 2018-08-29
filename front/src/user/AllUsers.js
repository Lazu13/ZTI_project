import React, {Component} from 'react';
import {deleteUser, getUserLike} from '../util/APIUtils'
import {AutoComplete, notification} from 'antd';
import './AllUsers.css'

class AllUsers extends Component {
    constructor(props) {
        super(props);
        this.state = {
            searchUser: [],
            isLoading: false
        };
        this.clearData = this.clearData.bind(this);
        this.handleSearch = this.handleSearch.bind(this);
        this.onSelect = this.onSelect.bind(this);
    }

    clearData() {
        this.setState({
            searchUser: [],
            isLoading: false
        });
    }

    onSelect = (value) => {
        let promise = deleteUser(value);

        if (!promise) {
            return;
        }

        this.setState({
            isLoading: true
        });

        promise
            .then(response => {
                this.setState({
                    searchUser: [],
                    isLoading: false
                });
                this.clearData();
                notification.info({
                    message: 'ZTI',
                    description: "Usunięto użytkownika"
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
    };

    handleSearch = (value) => {
        let promise = getUserLike(value, 0, 5);

        if (!promise) {
            return;
        }

        this.setState({
            isLoading: true
        });

        promise
            .then(response => {
                const users = [];
                const data = response.content;
                data.forEach((data, dataIndex) => {
                    users.push({
                        key: data.id,
                        username: data.username
                    })
                });

                this.setState({
                    searchUser: users,
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
    };

    render() {
        let searchUser = this.state.searchUser;

        return (
            <div className="user-container">
                <h1 className="page-title">Usuń użytkownika</h1>
                <AutoComplete
                    dataSource={searchUser.map(searchUser => (
                            <AutoComplete.Option key={searchUser.key}>
                                {searchUser.username}
                            </AutoComplete.Option>
                        )
                    )}
                    size="large"
                    onSelect={(a) => this.onSelect(a)}
                    onSearch={this.handleSearch}
                    placeholder="Nazwa użytkownika">
                </AutoComplete>
            </div>
        )
    }
}

export default AllUsers