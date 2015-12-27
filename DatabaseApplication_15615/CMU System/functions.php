<?php

include "config.php";

/*
 * For all functions $dbh is a database connection
 */

/*
 * @return handle to database connection
 */
function db_connect($host, $port, $db, $user, $pw) { 
	$port = pg_escape_string($port);
	$db = pg_escape_string($db);
	$user = pg_escape_string($user);
	$pw = pg_escape_string($pw);
	
	$str_connect = sprintf("host = %s port = %s dbname = %s user = %s password = %s", 
						   $host, $port, $db, $user, $pw);
	
	$dbh = pg_connect($str_connect) or die ('Fail to connect the database.');
	
	// error_log("here");
	// echo pg_last_error($dbh);
	
	return $dbh;
}

/*
 * Close database connection
 */ 
function close_db_connection($dbh) {
	pg_close($dbh);
}

/*
 * Login if user and password match
 * Return associative array of the form:
 * array(
 *		'status' =>  (1 for success and 0 for failure)
 *		'userID' => '[USER ID]'
 * )
 */
function login($dbh, $user, $pw) {
	$user = pg_escape_string($user);
	$pw = pg_escape_string($pw);
	
    $sql = pg_prepare($dbh, "login",
		   'SELECT * FROM player 
		    WHERE username = $1 AND password = $2');
    $sql = pg_execute($dbh, "login", array($user, $pw));

    if (!$sql || pg_num_rows($sql) == 0) {
        return array("status" => 0);
    }
    else {
        return array("status" => 1, "userID" => $user);
    }
}

/*
 * Check whether the user exists in the database
 * Return associative array of the form:
 * array(
 *		'status' =>   (1 for success and 0 for failure)
 *		'exists' => (true or false)
 * )
 */
function player_exists($dbh, $user) {
	$user = pg_escape_string($user);
	
	$sql = pg_prepare($dbh, "check_player_exist", 
		   'SELECT * FROM player 
			WHERE username = $1');
	$sql = pg_execute($dbh, "check_player_exist", array($user));

	if($sql) {
		if(pg_num_rows($sql) > 0) {
			return array("status" => 1, "exists" => true);
		} else {
			return array("status" => 1, "exists" => false);
		}
		
	} else {
		return array("status" => 0);
	}	
}

/*
 * Register user with given password 
 * Return associative array of the form:
 * array(
 *		'status' =>   (1 for success and 0 for failure)
 *		'userID' => '[USER ID]'
 * )
 */
function register($dbh, $user, $pw) {	
	$user = pg_escape_string($user);
	$pw = pg_escape_string($pw);
	
	// check whether the username exists
	$exists = player_exists($dbh, $user);
	if($exists["status"] == 0 || $exists["exists"]) {
		return array("status" => 0);
	}
	
	// insert the user
	$sql_register = pg_prepare($dbh, "register", 
					'INSERT INTO player 
					 VALUES ($1, $2)');
	$sql_register = pg_execute($dbh, "register", array($user, $pw));

	if($sql_register) {
		return array("status" => 1, "userID" => $user);
	} else {
		return array("status" => 0);
	}	
}

/*
 * Check whether the post exists in the database
 * Return associative array of the form:
 * array(
 *		'status' =>   (1 for success and 0 for failure)
 *		'exists' => (true or false)
 * )
 */
function post_exists($dbh, $pid) {	
	$pid = pg_escape_string($pid);
	
	$sql = pg_prepare($dbh, "check_post_exist", 
		   'SELECT * FROM post 
			WHERE postid = $1');
	$sql = pg_execute($dbh, "check_post_exist", array($pid));

	if($sql) {
		if(pg_num_rows($sql) > 0) {
			return array("status" => 1, "exists" => true);
		} else {
			return array("status" => 1, "exists" => false);
		}
		
	} else {
		return array("status" => 0);
	}	
}

/*
 * Make a new post.
 * Return associative array of the form:
 * array(
 *		'status' =>   (1 for success and 0 for failure)
 		'pID' => new post id
 * )
 */
function post_post($dbh, $title, $msg, $coorx, $coory, $me) {
	$title = pg_escape_string($title);
	$msg = pg_escape_string($msg);
	$coorx = pg_escape_string($coorx);
	$coory = pg_escape_string($coory);
	
	// insert the post
	$sql_insert = pg_prepare($dbh, "add_post", 
		   		  'INSERT INTO post(title, body, timestamp, username, position_x, position_y)
		    	   VALUES($1, $2, $3, $4, $5, $6)');
    $sql_insert = pg_execute($dbh, "add_post", 
							 array($title, $msg, time(), $me, $coorx, $coory));

    if (!$sql_insert) {
        return array("status" => 0);
    }
    
	// get the postid
	$sql_postid = pg_prepare($dbh, "get_pid", 
			      'SELECT postid FROM post 
				   WHERE title = $1 AND body = $2 AND username = $3
				   		 AND position_x = $4 AND position_y = $5');
	$sql_postid = pg_execute($dbh, "get_pid", array($title, $msg, $me, $coorx, $coory)); 
		
	if($sql_postid && pg_num_rows($sql_postid) == 1) {
		$post = pg_fetch_array($sql_postid);
		return array("status" => 1, "pID" => $post["postid"]);
	} else {
		return array("status" => 0);
	}
}

/*
 * Check whether the tag exists in hashtag
 * Return associative array of the form:
 * array(
 *		'status' =>   (1 for success and 0 for failure)
 *		'exists' => (true or false)
 * )
 */
function tag_exists($dbh, $tagname) {
	$tagname = pg_escape_string($tagname);
	
	$sql = pg_prepare($dbh, "check_tag_exists",
		   'SELECT * FROM hashtag 
		    WHERE tagname = $1');
	$sql = pg_execute($dbh, "check_tag_exists", array($tagname));

	if($sql) {
		if(pg_num_rows($sql) > 0) {
			return array("status" => 1, "exists" => true);
		} else {
			return array("status" => 1, "exists" => false);
		}
		
	} else {
		return array("status" => 0);
	}	
}

/*
 * Attach a hashtag value to a post. 
 * Optional: If no one has used this hashtag before, also call create_hashtag() to insert into hashtag table.
 * Return associative array of the form:
 * array(
 *		'status' =>   (1 for success and 0 for failure)
 * )
 */
function attach_hashtag($dbh, $pid, $tagname) {
	$pid = pg_escape_string($pid);
	$tagname = pg_escape_string($tagname);
	
	// check whether the post exists
	$exists_post = post_exists($dbh, $pid);
	if($exists_post["status"] == 0 || !$exists_post["exists"]) {
		return array("status" => 0);
	}
	
	// check whether the tag exists
	$exists_tag = tag_exists($dbh, $tagname);
	if($exists_tag["status"] == 0) {
		return array("status" => 0);
	}
	
	// if need to create new tag
	if(!$exists_tag["exists"]) {
		$sql_create = create_hashtag($dbh, $tagname);

		if(!$sql_create || $sql_create["status"] == 0) {
			return array("status" => 0);
		}
	}
	
	// add hashtag to post
	$sql_add = pg_prepare($dbh, "add_post_tag", 
			   'INSERT INTO posttag
			    VALUES ($1, $2)');
	$sql_add = pg_execute($dbh, "add_post_tag", array($pid,  $tagname));
	
	if ($sql_add) {
        return array("status" => 1);
    }
    else {
        return array("status" => 0);
    }
}

/*
 * Create a hashtag if not exists 
 * Return associative array of the form:
 * array(
 *		'status' =>   (1 for success and 0 for failure)
 * )
 */
function create_hashtag($dbh, $tagname) {
	$tagname = pg_escape_string($tagname);
	
	// check whether the tag exists
	$exists_tag = tag_exists($dbh, $tagname);
	if($exists_tag["status"] == 0 || $exists_tag["exists"]) {
		return array("status" => 0);
	}

	$sql = pg_prepare($dbh, "create_hashtag", 
		   'INSERT INTO hashtag
			VALUES ($1)');
	$sql = pg_execute($dbh, "create_hashtag", array($tagname));

	if($sql) {
		return array("status" => 1);
	} else {
		return array("status" => 0);
	}
}

/*
 * Get all posts with a given hashtag
 * Order by time of the post (going backward in time), and break ties by sorting by the username alphabetically
 * Return associative array of the form:
 * array(
 *		'status' => (1 for success and 0 for failure)
 *		'posts' => [ (Array of post objects) ]
 * )
 * Each post should be of the form:
 * array(
 *		'pID' => (INTEGER)
 *		'username' => (USERNAME)
 *		'title' => (TITLE OF POST)
 *      'content' => (CONTENT OF POST)
 *		'time' => (UNIXTIME INTEGER)
 *		'coorX' => (coordination x, INTEGER)
 *		'coorY' => (coordination y, INTEGER)
 * )
 */
function search_post_by_tag($dbh, $tagname) {
	$tagname = pg_escape_string($tagname);
	
	$sql = pg_prepare($dbh, "search_tag", 
		   'SELECT * FROM post, posttag 
	     	WHERE post.postid = posttag.postid
				  AND tagname = $1');
    $sql = pg_execute($dbh, "search_tag", array($tagname));
	
	if (!$sql) {
        return array("status" => 0);
    }
	
	$posts = array();
	while ($row = pg_fetch_array($sql)) {
		$new_post = array("pID" => $row["postid"],
						  "username" => $row["username"],
						  "title" => $row["title"],
						  "content" => $row["body"],
						  "time" => $row["timestamp"],
						  'coorX' => $row["position_x"],
						  'coorY' => $row["position_y"]);
		array_push($posts, $new_post);
	}
	return array("status" => 1, "posts" => $posts);
}


/*
 * Get timeline of $count most recent posts that were written before timestamp $start
 * For a user $user, the timeline should include all posts.
 * Order by time of the post (going backward in time), and break ties by sorting by the username alphabetically
 * Return associative array of the form:
 * array(
 *		'status' => (1 for success and 0 for failure)
 *		'posts' => [ (Array of post objects) ]
 * )
 * Each post should be of the form:
 * array(
 *		'pID' => (INTEGER)
 *		'username' => (USERNAME)
 *		'title' => (TITLE OF POST)
 *      'content' => (CONTENT OF POST)
 *		'time' => (UNIXTIME INTEGER)
 *		'coorX' => (coordination x, INTEGER)
 *		'coorY' => (coordination y, INTEGER)
 * )
 */
function get_timeline($dbh, $user, $count = 10, $start = PHP_INT_MAX) {
	$user = pg_escape_string($user);
	
	// check whether the player exists
	$exists = player_exists($dbh, $user);
	if($exists["status"] == 0 || !$exists["exists"]) {
		return array("status" => 0);
	}
	
	// get the timelines
	$sql = pg_prepare($dbh, "get_timeline", 
		   "SELECT * FROM post 
			WHERE timestamp < $1 
			ORDER BY timestamp DESC, username 
			LIMIT $2");

    $sql = pg_execute($dbh, "get_timeline", array($start, $count));

    if ($sql) {
        $posts = array();
        while ($row = pg_fetch_array($sql)) {
            $new_post = array('pID' => $row["postid"],
							  'username' => $row["username"],
							  'title' => $row["title"],
							  'content' => $row["body"],
							  'time' => $row["timestamp"],
							  'coorX' => $row["position_x"],
							  'coorY' => $row["position_y"]);
		array_push($posts, $new_post);
		}
        return array("status" => 1, "posts" => $posts);
    }
	
    return array("status" => 0);
}

/*
 * Get list of $count most recent posts that were written by user $user before timestamp $start
 * Order by time of the post (going backward in time)
 * Return associative array of the form:
 * array(
 *		'status' =>   (1 for success and 0 for failure)
 *		'posts' => [ (Array of post objects) ]
 * )
 * Each post should be of the form:
 * array(
 *		'pID' => (INTEGER)
 *		'username' => (USERNAME)
 *		'title' => (TITLE)
 *		'content' => (CONTENT)
 *		'time' => (UNIXTIME INTEGER)
 *		'coorX' => (coordination x, INTEGER)
 *		'coorY' => (coordination y, INTEGER)
 * )
 */
function get_user_posts($dbh, $user, $count = 10, $start = PHP_INT_MAX) {
	$user = pg_escape_string($user);
	
	// check whether the player exists
	$exists = player_exists($dbh, $user);
	if($exists["status"] == 0 || !$exists["exists"]) {
		return array("status" => 0);
	}
	
    $sql = pg_prepare($dbh, "get_user_posts", 
    	   'SELECT * FROM post 
    	 	WHERE timestamp < $1 AND username = $2 
			ORDER BY timestamp DESC 
			LIMIT $3');
    $sql = pg_execute($dbh, "get_user_posts", array($start, $user, $count));

    if (!$sql || pg_num_rows($sql) == 0) {
        return array("status" => 0);
    }
	
	$posts = array();
	while ($row = pg_fetch_array($sql)) {
		$new_post = array('pID' => $row["postid"],
						  'username' => $row["username"],
						  'title' => $row["title"],
						  'content' => $row["body"],
						  'time' => $row["timestamp"],
						  'coorX' => $row["position_x"],
						  'coorY' => $row["position_y"]);
	array_push($posts, $new_post);
	}
	
	return array("status" => 1, "posts" => $posts);
}

/*
 * Deletes a post given $user name and $pID.
 * $user must be the one who posted the post $pID.
 * Return associative array of the form:
 * array(
 *		'status' =>   (1 for success. 0 or 2 for failure)
 * )
 */
function delete_post($dbh, $user, $pID) {
	$user = pg_escape_string($user);
	$pID = pg_escape_string($pID);
	
	// check whether the player exists
	$exists_player = player_exists($dbh, $user);
	if($exists_player["status"] == 0 || !$exists_player["exists"]) {
		return array("status" => 0);
	}
	// check whether the post exists
	$exists_post = post_exists($dbh, $pID);
	if($exists_post["status"] == 0 || !$exists_post["exists"]) {
		return array("status" => 0);
	}
		
	// delete the records in post
	$sql_post = pg_prepare($dbh, "delete_post", 
		   		'DELETE FROM post 
		    	 WHERE postid = $1');
    $sql_post = pg_execute($dbh, "delete_post", array($pID));
	
    if (!$sql_post) {
    	return array("status" => 2);
	}
	
	// delete the records in vote
	$sql_vote = pg_prepare($dbh, "delete_vote", 
		   'DELETE FROM vote 
		    WHERE postid = $1');
    $sql_vote = pg_execute($dbh, "delete_vote", array($pID));
	
    if (!$sql_vote) {
    	return array("status" => 2);
	}
	
	// delete the records in posttage
	$sql_posttag = pg_prepare($dbh, "delete_posttag", 
		    	   'DELETE FROM posttag 
		    		WHERE postid = $1');
    $sql_posttag = pg_execute($dbh, "delete_posttag", array($pID));
	
    if (!$sql_posttag) {
    	return array("status" => 2);
	}
            
    return array("status" => 1);
}

/*
 * Records a "vote" for a post given logged-in user $me and $pID.
 * You don't have to call already_voted() in this function, since it's taken care of by our web application
 * You can assume that when vote_post() is called, $me hasn't yet voted for post with $pID
 * Return associative array of the form:
 * array(
 *		'status' =>   (1 for success. 0 for failure)
 * )
 */
function vote_post($dbh, $me, $pID) {
	$pID = pg_escape_string($pID);
	
    // check whether the post exists
	$exists_post = post_exists($dbh, $pID);
	if($exists_post["status"] == 0 || !$exists_post["exists"]) {
		return array("status" => 0);
	}
	
	// check whether it is owner's post
	$sql_owner = pg_prepare($dbh, "owner_vote", 
				 'SELECT * FROM post WHERE postid = $1 AND username = $2');
	$sql_owner = pg_execute($dbh, "owner_vote", array($pID, $me)); 
	if (!$sql_owner || pg_num_rows($sql_owner) > 0) {
		return array("status" => 0); 
	}
	
	// add records in vote
    $sql = pg_prepare($dbh, "add_vote", 
		   'INSERT INTO vote 
		    VALUES ($1, $2)');
    $sql = pg_execute($dbh, "add_vote", array($me, $pID));
	
    if ($sql) {
        return array("status" => 1);
	} else {
        return array("status" => 0);
    }
}


/*
 * Records a "unvote" for a post given logged-in user $me and $pID.
 * You don't have to call already_voted() in this function, since it's taken care of by our web application
 * You can assume that when unvote_post() is called, $me has already voted for post with $pID
 * Return associative array of the form:
 * array(
 *		'status' =>   (1 for success. 0 for failure)
 * )
 */
function unvote_post($dbh, $me, $pID) {
	$pID = pg_escape_string($pID);
	
    // check whether the post exists
	$exists_post = post_exists($dbh, $pID);
	if($exists_post["status"] == 0 || !$exists_post["exists"]) {
		return array("status" => 0);
	}
	
	// delete records in vote
    $sql = pg_prepare($dbh, "detele_vote", 
		   'DELETE FROM vote 
		    WHERE username = $1 AND postid = $2');
    $sql = pg_execute($dbh, "detele_vote", array($me, $pID));
	
    if ($sql) {
        return array("status" => 1);
	} else {
        return array("status" => 0);
    }
}


/*
 * Check if $me has already voted post $pID
 * Return true if user $me has voted for post $pID or false otherwise
 */
function already_voted($dbh, $me, $pID) {
	$pID = pg_escape_string($pID);
	
    // check whether the post exists
	$exists_post = post_exists($dbh, $pID);
	if($exists_post["status"] == 0 || !$exists_post["exists"]) {
		return array("status" => 0);
	}
	
    $sql = pg_prepare($dbh, "already_voted", 
		   'SELECT * FROM vote 
		    WHERE username = $1 AND postid = $2');
    $sql = pg_execute($dbh, "already_voted", array($me, $pID));
	
    if (!$sql || pg_num_rows($sql) == 0) {
        return false;
    } else {
        return true;
    }
}


/*
 * Find the $count most recent posts that contain the string $key
 * Order by time of the post and break ties by the username (sorted alphabetically A-Z)
 * Return associative array of the form:
 * array(
 *		'status' =>   (1 for success and 0 for failure)
 *		'posts' => [ (Array of Post objects) ]
 * )
 */
function search($dbh, $key, $count = 50) {
	$key = pg_escape_string($key);
	
    $sql = pg_prepare($dbh, "search_post", 
		   'SELECT * FROM post 
		    WHERE title LIKE $1 OR body LIKE $1 
		    ORDER BY timestamp DESC, username 
		    LIMIT $2');
	$sql = pg_execute($dbh, "search_post", array("%".$key."%", $count));
	
	if (!$sql) {
		return array("status" => 0);
	}
	
	$posts = array();
	while ($row = pg_fetch_array($sql)) {
		$new_post = array('pID' => $row["postid"],
						  'username' => $row["username"],
						  'title' => $row["title"],
						  'content' => $row["body"],
						  'time' => $row["timestamp"],
						  'coorX' => $row["position_x"],
						  'coorY' => $row["position_y"]);
	array_push($posts, $new_post);
	}
	
	return array("status" => 1, "posts" => $posts);	
}

/*
 * Find the $count most recent posts that contain the string $key, and is within the range $range of ($coorX, $coorY)
 * Order by time of the post and break ties by the username (sorted alphabetically A-Z)
 * Return associative array of the form:
 * array(
 *		'status' =>   (1 for success and 0 for failure)
 *		'posts' => [ (Array of Post objects) ]
 * )
 * Each post should be of the form:
 * array(
 *		'pID' => (INTEGER)
 *		'username' => (USERNAME)
 *		'title' => (TITLE)
 *		'content' => (CONTENT)
 *		'time' => (UNIXTIME INTEGER)
 *		'coorX' => (coordination x, INTEGER)
 *		'coorY' => (coordination y, INTEGER)
 * )
 */
function search_range($dbh, $key, $coorx, $coory, $range, $count = 50) {
	$key = pg_escape_string($key);
	$coorx = pg_escape_string($coorx);
	$coory = pg_escape_string($coory);
	$range = pg_escape_string($range);
	
    $sql = pg_prepare($dbh, "search_post", 
		   'SELECT * FROM post 
		    WHERE title LIKE $1 OR body LIKE $1 
				  AND ABS(position_x - $2) < $4
				  AND ABS(position_y - $3) < $4
		    ORDER BY timestamp DESC, username 
		    LIMIT $5');
	$sql = pg_execute($dbh, "search_post", array("%".$key."%", $coorx, $coory, $range, $count));
	
	if (!$sql) {
		return array("status" => 0);
	}
	
	$posts = array();
	while ($row = pg_fetch_array($sql)) {
		$new_post = array('pID' => $row["postid"],
						  'username' => $row["username"],
						  'title' => $row["title"],
						  'content' => $row["body"],
						  'time' => $row["timestamp"],
						  'coorX' => $row["position_x"],
						  'coorY' => $row["position_y"]);
		array_push($posts, $new_post);
	}
	
	return array("status" => 1, "posts" => $posts);	
}


/*
 * Get the number of votes of post $pID
 * Return associative array of the form:
 * array(
 *		'status' =>   (1 for success and 0 for failure)
 *		'count' => (The number of votes)
 * )
 */
function get_num_votes($dbh, $pID) {
	$pID = pg_escape_string($pID);
	
	// check whether the post exists
	$exists_post = post_exists($dbh, $pID);
	if($exists_post["status"] == 0 || !$exists_post["exists"]) {
		return array("status" => 0);
	}
	
	$sql = pg_prepare($dbh, "get_num_votes", 
	   	   'SELECT COUNT(*) AS count FROM vote 
			WHERE postid = $1');
	$sql = pg_execute($dbh, "get_num_votes", array($pID));
	
	if ($sql) {
		$row = pg_fetch_array($sql);
		return array("status" => 1, "count" => $row["count"]);
	} else {
		return array("status" => 0);
	}
}

/*
 * Get the number of posts of user $uID
 * Return associative array of the form:
 * array(
 *		'status' =>   (1 for success and 0 for failure)
 *		'count' => (The number of posts)
 * )
 */
function get_num_posts($dbh, $uID) {
	$uID = pg_escape_string($uID);
	
	// check whether the player exists
	$exists_player = player_exists($dbh, $uID);
	if($exists_player["status"] == 0 || !$exists_player["exists"]) {
		return array("status" => 0);
	}
	
	// get the number of posts
	$sql = pg_prepare($dbh, "get_num_posts", 
		   'SELECT COUNT(*) AS count FROM post
		    WHERE username = $1');
    $sql = pg_execute($dbh, "get_num_posts", array($uID));
	
	if (!$sql) {
        return array("status" => 0);
    }
    else {
        $row = pg_fetch_array($sql);
        return array("status" => 1, "count" => $row["count"]);
    }
}

/*
 * Get the number of hashtags used by user $uID
 * Return associative array of the form:
 * array(
 *		'status' =>   (1 for success and 0 for failure)
 *		'count' => (The number of hashtags)
 * )
 */
function get_num_tags_of_user($dbh, $uID) {
	$uID = pg_escape_string($uID);
	
	// check whether the player exists
	$exists_player = player_exists($dbh, $uID);
	if($exists_player["status"] == 0 || !$exists_player["exists"]) {
		return array("status" => 0);
	}
	
	// get the number of tags by user
	$sql = pg_prepare($dbh, "count_user_tags", 
		   'SELECT COUNT(DISTINCT tagname) AS count 
		    FROM posttag NATURAL JOIN post
		    WHERE username = $1');
    $sql = pg_execute($dbh, "count_user_tags", array($uID));
	
	if (!$sql) {
        return array("status" => 0);
    }
    else {
        $row = pg_fetch_array($sql);
        return array("status" => 1, "count" => $row["count"]);
    }
}

/*
 * Get the number of votes user $uID made
 * Return associative array of the form:
 * array(
 *		'status' =>   (1 for success and 0 for failure)
 *		'count' => (The number of likes)
 * )
 */
function get_num_votes_of_user($dbh, $uID) {
	$uID = pg_escape_string($uID);
	
	// check whether the player exists
	$exists_player = player_exists($dbh, $uID);
	if($exists_player["status"] == 0 || !$exists_player["exists"]) {
		return array("status" => 0);
	}
	
	// get the number of votes by user
	$sql = pg_prepare($dbh, "count_user_votes", 
		   'SELECT COUNT(*) AS count 
		    FROM vote
		    WHERE username = $1');
    $sql = pg_execute($dbh, "count_user_votes", array($uID));
	
	if (!$sql) {
        return array("status" => 0);
    }
    else {
        $row = pg_fetch_array($sql);
        return array("status" => 1, "count" => $row["count"]);
    }
}

/*
 * Get the list of $count users that have posted the most
 * Order by the number of posts (descending), and then by username (A-Z)
 * Return associative array of the form:
 * array(
 *		'status' =>   (1 for success and 0 for failure)
 *		'users' => [ (Array of user IDs) ]
 * )
 */
function get_most_active_users($dbh, $count = 10) {
    $sql = pg_prepare($dbh, "most_active_user", 
		   'SELECT username, COUNT(*) AS count FROM post 
		    GROUP BY username 
			ORDER BY count DESC, username 
			LIMIT $1');
    $sql = pg_execute($dbh, "most_active_user", array($count));
	
	if (!$sql) {
        return array("status" => 0);
    }
	
	$ids = array();
	while ($row = pg_fetch_array($sql)) {
		array_push($ids, $row["username"]);
	}
	return array("status" => 1, "users" => $ids);
    
}

/*
 * Get the list of $count posts posted after $from that have the most votes.
 * Order by the number of votes (descending)
 * Return associative array of the form:
 * array(
 *		'status' =>   (1 for success and 0 for failure)
 *		'posts' => [ (Array of post objects) ]
 * )
 * Each post should be of the form:
 * array(
 *		'pID' => (INTEGER)
 *		'username' => (USERNAME)
 *		'title' => (TITLE)
 *		'content' => (CONTENT)
 *		'time' => (UNIXTIME INTEGER)
 *		'coorX' => (coordination x, INTEGER)
 *		'coorY' => (coordination y, INTEGER)
 * )
 */
function get_most_popular_posts($dbh, $count = 10, $from = 0) {
	$sql_pop = pg_prepare($dbh, "get_popular_posts", 
			   "SELECT post.postid, COUNT(*) AS count
			    FROM post, vote 
				WHERE post.postid = vote.postid AND timestamp > $1
			    GROUP BY post.postid 
			    ORDER BY count DESC 
			    LIMIT $2");
    $sql_pop = pg_execute($dbh, "get_popular_posts", array($from, $count));

	if (!$sql_pop) {
        return array("status" => 0);
    }

	$posts = array();
	while ($row = pg_fetch_array($sql_pop)) {
		$sql_posts = pg_prepare($dbh, "get_posts", 
					 'SELECT * FROM post
					  WHERE postid = $1');
		$sql_posts = pg_execute($dbh, "get_posts", array($row["postid"]));

		if (!$sql_posts) {
			return array("status" => 0);
		}
		
		$post_row = pg_fetch_array($sql_posts);
		$new_post = array('pID' => $post_row["postid"],
						  'username' => $post_row["username"],
						  'title' => $post_row["title"],
						  'content' => $post_row["body"],
						  'time' => $post_row["timestamp"],
						  'coorX' => $post_row["position_x"],
						  'coorY' => $post_row["position_y"]);
		array_push($posts, $new_post);
	}
	return array("status" => 1, "posts" => $posts);
}

/*
 * Get the list of $count hashtags that have been used 
 * Order by the number of times being used (descending), and then by tagname (A-Z)
 * Return associative array of the form:
 * array(
 *		'status' =>   (1 for success and 0 for failure)
 *		'tags' => [ (Array of tags) ]
 * )
 * Then each tag should have the form
 * array(
 *		'tagname' =>  (tagname)
 *		'occurence' => (number of times that is used)
 * )
 */
function get_most_popular_tags($dbh, $count = 5) {
	$sql = pg_prepare($dbh, "get_popular_tags", 
		   "SELECT tagname, COUNT(*) AS count
			FROM posttag JOIN vote ON posttag.postid = vote.postid 
			GROUP BY tagname 
			ORDER BY count DESC, tagname 
			LIMIT $1");
    $sql = pg_execute($dbh, "get_popular_tags", array($count));
	
	if (!$sql || pg_num_rows($sql) == 0) {
        return array("status" => 0);
    }
	
	$tags = array();
	while ($row = pg_fetch_array($sql)) {
		$new_tag = array('tagname' => $row["tagname"],
						 'occurence' => $row["count"]);
		array_push($tags, $new_tag);
	}
	
	return array("status" => 1, "tags" => $tags);	
}

/*
 * Get the list of $count tag pairs that have been used together
 * Avoid duplicate pairs like (#foo #bar) and (#bar #foo). 
 * They should only be counted once with alphabetic order (#bar #foo)
 * Order by the number of times being used (descending)
 * Return associative array of the form:
 * array(
 *		'status' =>   (1 for success and 0 for failure)
 *		'tagpairs' => [ (Array of tags) ]
 * )
 * Then each tagpair should have the form
 * array(
 *		'tagname1' => (tagname1)
 *		'tagname2' => (tagbane2)
 *		'occurence' => (number of times that occurs)
 * )
 */
function get_most_popular_tag_pairs($dbh, $count = 5) {
	$sql = pg_prepare($dbh, "get_popular_tag_pairs", 
		   'SELECT tag1, tag2, COUNT(DISTINCT postid) AS count
		    FROM ( SELECT h1.postid, h1.tagname AS tag1, h2.tagname AS tag2 
				   FROM posttag h1 JOIN posttag h2 
				   ON h1.postid = h2.postid 
				   WHERE h1.tagname < h2.tagname) AS tagpairs
			GROUP BY tag1, tag2 
			ORDER BY count DESC, tag1, tag2 
			LIMIT $1');
	$sql = pg_execute($dbh, "get_popular_tag_pairs", array($count)); 
	
	if (!$sql || pg_num_rows($sql) == 0) {
		return array("status" => 0); 
	}
	
	$pairs = array();	
	while ($row = pg_fetch_array($sql)) {
		$new_pair = array('tagname1' => $row["tag1"],
						  'tagname2' => $row["tag2"],
						  'occurence' => $row["count"]); 
		array_push($pairs, $new_pair);
	}
	return array("status" => 1, "tagpairs" => $pairs);
}

/*
 * Recommend posts for user $user.
 * A post $p is a recommended post for $user if like minded users of $user also voted for the post,
 * where like minded users are users who voted for the posts $user voted for.
 * Result should not include posts $user voted for.
 * Rank the recommended posts by how many like minded users voted for the posts.
 * The set of like minded users should not include $user self.
 *
 * Return associative array of the form:
 * array(
 *    'status' =>   (1 for success and 0 for failure)
 *    'posts' => [ (Array of post objects) ]
 * )
 * Each post should be of the form:
 * array(
 *		'pID' => (INTEGER)
 *		'username' => (USERNAME)
 *		'title' => (TITLE)
 *		'content' => (CONTENT)
 *		'time' => (UNIXTIME INTEGER)
 *		'coorX' => (coordination x, INTEGER)
 *		'coorY' => (coordination y, INTEGER)
 * )
 */
function get_recommended_posts($dbh, $count = 10, $user) {
	$user = pg_escape_string($user);
	
	$sql = pg_prepare($dbh, "get_popular_tag_pairs", 
		   'SELECT * FROM post 
		    NATURAL JOIN ( SELECT postid, COUNT(DISTINCT username) AS count
						   FROM (SELECT v2.username 
						         FROM vote v1 JOIN vote v2 
								 ON v1.postid = v2.postid AND v1.username = v2.username 
								 WHERE v1.username = $1) AS vote_for
						   NATURAL JOIN vote 
						   WHERE postid NOT IN (SELECT postid FROM vote 
						   						WHERE username = $1) AS owner_post
						   GROUP BY postid 
						   ORDER BY count DESC 
						   LIMIT $2 ) AS unordered_post
		    ORDER BY count DESC, username');
	$sql = pg_execute($dbh, "get_popular_tag_pairs", array($user, $count)); 
	
	if (!$sql || pg_num_rows($sql) == 0) {
		return array("status" => 0); 
	}
	
	$posts = array();
	while ($row = pg_fetch_array($sql)) { 
		$new_post = array('pID' => $row["postid"],
						  'username' => $row["username"],
						  'title' => $row["title"],
						  'content' => $row["body"],
						  'time' => $row["timestamp"],
						  'coorX' => $row["position_x"],
						  'coorY' => $row["position_y"]);
		
		array_push($posts, $new_post); 
	}
	
	return array("status" => 1, "posts" => $posts);
}


/*
 * Delete all tables in the database and then recreate them (without any data)
 * Return associative array of the form:
 * array(
 *		'status' =>   (1 for success and 0 for failure)
 * )
 */
function reset_database($dbh) {
	// Delete all tables
	$sql_drop_vote = pg_prepare($dbh, "drop_vote", "DROP TABLE IF EXISTS vote;");
	$sql_drop_vote = pg_execute($dbh, "drop_vote", array());
	
	$sql_drop_post = pg_prepare($dbh, "drop_post", "DROP TABLE IF EXISTS post;");
	$sql_drop_post = pg_execute($dbh, "drop_post", array());
	
	$sql_drop_player = pg_prepare($dbh, "drop_player", 'DROP TABLE IF EXISTS player');
	$sql_drop_player = pg_execute($dbh, "drop_player", array());
	
	$sql_drop_hashtag = pg_prepare($dbh, "drop_hashtag", "DROP TABLE IF EXISTS hashtag;");
	$sql_drop_hashtag = pg_execute($dbh, "drop_hashtag", array());
	
	$sql_drop_posttag = pg_prepare($dbh, "drop_posttag", "DROP TABLE IF EXISTS posttag;");
	$sql_drop_posttag = pg_execute($dbh, "drop_posttag", array());
	
	// $sql_drop_seq = pg_query($dbh, "DROP SEQUENCE IF EXISTS post_postid_seq;");
	
	if(!$sql_drop_player || !$sql_drop_post || !$sql_drop_vote 
	   || !$sql_drop_hashtag || !$sql_drop_posttag) {
		return array("status" => 0);
	}
	
	// Recreate tables
	$sql_create_player = pg_prepare($dbh, "create_player", 
					     'CREATE TABLE player ( username varchar(50), 
												password varchar(255), 
												PRIMARY KEY (username),
												CHECK (char_length(username) > 2))');
	$sql_create_player = pg_execute($dbh, "create_player", array());
	
	$sql_create_post = pg_prepare($dbh, "create_post", 
					   'CREATE TABLE post ( postid serial, 
											title varchar(20), 
											body varchar(42), 
											timestamp bigint, 
											username varchar(50),
											position_x int,
											position_y int,
											PRIMARY KEY (postid),
											FOREIGN KEY (username) REFERENCES player(username))');
	$sql_create_post = pg_execute($dbh, "create_post", array());
	
	$sql_create_vote = pg_prepare($dbh, "create_vote", 
					   'CREATE TABLE vote ( username varchar(50), 
											postid serial,
											PRIMARY KEY (username, postid),
											FOREIGN KEY (username) REFERENCES player(username),
											FOREIGN KEY (postid) REFERENCES post(postid))');
	$sql_create_vote = pg_execute($dbh, "create_vote", array());
	
	$sql_create_hashtag = pg_prepare($dbh, "create_hashtag", 
					      'CREATE TABLE hashtag ( tagname varchar(50),
						   						  PRIMARY KEY (tagname))');
	$sql_create_hashtag = pg_execute($dbh, "create_hashtag", array());
	
	$sql_create_posttag = pg_prepare($dbh, "create_posttag", 
					      'CREATE TABLE posttag ( postid serial, tagname varchar(50),
						   PRIMARY KEY (postid, tagname))');
	$sql_create_posttag = pg_execute($dbh, "create_posttag", array());
	
	if(!$sql_create_player || !$sql_create_post || !$sql_create_vote 
	   || !$sql_create_hashtag || !$sql_create_posttag) {
		return array("status" => 0);
	} else {
		return array("status" => 1);
	}

}

?>
