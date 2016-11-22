// from https://devdactic.com/restful-api-user-authentication-1/
 
// load up the user model
var secrets = require('../config/secrets');
var user_services = require('../services/users')

var JwtStrategy = require('passport-jwt').Strategy;
var ExtractJwt = require('passport-jwt').ExtractJwt;

module.exports = function (passport) {
    var opts = {};
    opts.jwtFromRequest = ExtractJwt.fromAuthHeader();
    opts.secretOrKey = secrets.jwt_secret;
    // new
    passport.use(new JwtStrategy(opts, function (jwt_payload, done) {
        user_services.userExists(jwt_payload, function (rows) {
            if (rows.length != 0) {
                done(null, rows[0]);
            }
            // if (user) {
            //     return done(err, false);
            // } 
            else {
                done(null, false);
            }
        });
    }));


    // end new
    // passport.use(new JwtStrategy(opts, function (jwt_payload, done) {
    //     user_services.userExists(jwt_payload.username, function (rows) {
    //         if (rows.length != 0) {
    //             done(null, rows[0]);
    //         }
    //         // if (user) {
    //         //     return done(err, false);
    //         // } 
    //         else {
    //             done(null, false);
    //         }
    //     });
    // }));
};
