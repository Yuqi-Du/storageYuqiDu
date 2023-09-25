scenario.run({
    'driver':       'http',
    'tags':         'name:"write.*"',
    'cycles':       '10000',
    'threads':      '100',
    'errors':       'timer,warn',
    'docscount':    '10000',
    'jsonapi_host': 'a32c9d78-6948-491f-b843-9edb39ce1b0b-us-east-2.apps.astra.datastax.com',
    'auth_token':   'AstraCS:ZBXmMhtJWQHIlIaRLhCrfYvh:bf3220b3e553dac99fbc203e502820f0233386d2dd56ccd2d16b9a6c89e5fd4f',
    'jsonapi_port': '443',
    'protocol':     'https',
    'path_prefix':  '/api/json',
    'namespace':    'testks0',
    'workload':     'http-jsonapi-vector-crud',
    'alias':        'write',
    'labels':       'workload:$http_jsonapi_vector_crud'
});
scenario.run({
    'driver':       'http',
    'tags':         'name:"update.*"',
    'cycles':       '10000',
    'threads':      '100',
    'errors':       'timer,warn',
    'docscount':    '10000',
    'jsonapi_host': 'a32c9d78-6948-491f-b843-9edb39ce1b0b-us-east-2.apps.astra.datastax.com',
    'auth_token':   'AstraCS:ZBXmMhtJWQHIlIaRLhCrfYvh:bf3220b3e553dac99fbc203e502820f0233386d2dd56ccd2d16b9a6c89e5fd4f',
    'jsonapi_port': '443',
    'protocol':     'https',
    'path_prefix':  '/api/json',
    'namespace':    'testks0',
    'workload':     'http-jsonapi-vector-crud',
    'alias':        'update',
    'labels':       'workload:$http_jsonapi_vector_crud'
});
