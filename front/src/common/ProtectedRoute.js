import {Redirect, Route} from "react-router-dom";
import React from "react";

const ProtectedRoute = ({component: Component, authenticated, isAdmin, ...rest}) => (
    <Route
        {...rest}
        render={props =>
            (isAdmin && authenticated) ? (
                <Component {...rest} {...props} />
            ) : (
                <Redirect
                    to={{
                        pathname: '/login',
                        state: {from: props.location}
                    }}
                />
            )
        }
    />
);

export default ProtectedRoute