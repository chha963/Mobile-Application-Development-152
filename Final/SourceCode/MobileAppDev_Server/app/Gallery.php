<?php
/**
 * Created by PhpStorm.
 * User: chha963
 * Date: 5/8/2016
 * Time: 3:06 PM
 */

namespace App;

use Illuminate\Database\Eloquent\Model;

class Gallery extends Model {

    /**
     * The table associated with the model.
     *
     * @var string
     */
    protected $table = 'gallery_url';

    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'p_id', 'url'
    ];

} 