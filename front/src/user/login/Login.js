import React, {Component} from 'react';
import {login} from '../../util/APIUtils';
import './Login.css';
import {Link} from 'react-router-dom';
import {ACCESS_TOKEN} from '../../constants';

import {Form, Input, Button, Icon, notification} from 'antd';

const FormItem = Form.Item;

class Login extends Component {
    render() {
        const AntWrappedLoginForm = Form.create()(LoginForm);
        return (
            <div className="login-container">
                <h1 className="page-title">Logowanie</h1>

                <div className="login-content">
                    <AntWrappedLoginForm onLogin={this.props.onLogin}/>
                </div>
            </div>
        );
    }
}

class LoginForm extends Component {
    constructor(props) {
        super(props);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(event) {
        event.preventDefault();
        this.props.form.validateFields((err, values) => {
            if (!err) {
                const loginRequest = Object.assign({}, values);
                login(loginRequest)
                    .then(response => {
                        localStorage.setItem(ACCESS_TOKEN, response.access_token);
                        this.props.onLogin();
                    }).catch(error => {
                    if (error.status === 401) {
                        notification.error({
                            message: 'ZTI',
                            description: 'Hasło lub login jest niepoprawne! '
                        });
                    } else {
                        notification.error({
                            message: 'ZTI',
                            description: error.message || 'Przykro nam, coś poszło nie tak. Spróbuj ponownie!'
                        });
                    }
                });
            }
        });
    }

    render() {
        const {getFieldDecorator} = this.props.form;
        return (
            <Form onSubmit={this.handleSubmit} className="login-form">
                <FormItem>
                    {getFieldDecorator('username', {
                        rules: [{required: true, message: 'Wprowadź nazwe!'}],
                    })(
                        <Input
                            prefix={<Icon type="user"/>}
                            size="large"
                            name="username"
                            placeholder="Nazwa użytkownika"/>
                    )}
                </FormItem>
                <FormItem>
                    {getFieldDecorator('password', {
                        rules: [{required: true, message: 'Wprowadź hasło'}],
                    })(
                        <Input
                            prefix={<Icon type="lock"/>}
                            size="large"
                            name="password"
                            type="password"
                            placeholder="Hasło"/>
                    )}
                </FormItem>
                <FormItem>
                    <Button type="primary" htmlType="submit" size="large" className="login-form-button">Zaloguj</Button>
                    Lub <Link to="/signup">Zarejestruj!</Link>
                </FormItem>
            </Form>
        );
    }
}


export default Login;